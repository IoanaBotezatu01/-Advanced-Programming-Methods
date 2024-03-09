package controller;
import model.ADT.MyIHeap;
import model.ADT.MyIList;
import model.value.RefValue;
import model.value.Value;
import repository.IRepository;
import model.MyException;
import model.PrgState;
import model.ADT.MyIStack;
import model.stmt.IStmt;

import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Locale.filter;

public class Controller {
    private IRepository repository;
    private ExecutorService executor;
    public Controller(IRepository repository) {
        this.repository = repository;
    }
    public IRepository getRepository() {
        return repository;
    }
    public void setRepository(IRepository repository) {
        this.repository = repository;
    }
    public List<PrgState> getProgramStates(){
        return repository.getPrgList();
    }

    public MyIList<Value> getOut(PrgState state){
        return state.getOut();
    }

    public void oneStep() throws InterruptedException, MyException {
        executor = Executors.newFixedThreadPool(2);
        List<PrgState> programStates = removeCompletedPrg(repository.getPrgList());
        oneStepForAllPrg(programStates);
        conservativeGarbageCollector(programStates);
        executor.shutdownNow();
    }
    public void setProgramStates(List<PrgState> prgStates) {
        repository.setPrgList(prgStates);
    }

    public void allStep() throws InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        //remove the completed programs
        List<PrgState> prgList=removeCompletedPrg(repository.getPrgList());
        while(prgList.size() > 0){
            prgList.forEach(prg -> prg.getHeap().setContent(safeGarbageCollector(getAddrFromSymTable(prg.getSymTable().peek().getContent().values()), getAddrFromHeapTable(prg.getHeap().getContent().values()), prg)));
            oneStepForAllPrg(prgList);
            //remove the completed programs
            prgList=removeCompletedPrg(repository.getPrgList());
        }
        executor.shutdownNow();
        //HERE the repository still contains at least one Completed Prg
        // and its List<PrgState> is not empty. Note that oneStepForAllPrg calls the method
        //setPrgList of repository in order to change the repository

        // update the repository state
        repository.setPrgList(prgList);
    }
    public void displayPrgState(PrgState state) {
        System.out.println(state);
    }

    /*private void printS(){
        PrgState programState = repository.getCrtPrg();
        System.out.print("------------------------------------------------------\n");

        System.out.print("*****ExecutionStack*****\n");
        System.out.print(programState.getExeStack().toString() + "\n");

        System.out.print("*****OutputList*****\n");
        System.out.print(programState.getOut() + "\n");

        System.out.print("*****SymbolTable*****\n");
        System.out.print(programState.getSymTable().toString() + "\n");


        System.out.print("*****FileTable*****\n");
        System.out.print(programState.getFileTable().toString() + "\n");

        System.out.print("------------------------------------------------------\n");
    }*/

    public Map<Integer,Value> safeGarbageCollector(List<Integer> symTableAddr,List<Integer> heapRefAddress,PrgState program){
        MyIHeap<Integer,Value> heap= program.getHeap();
        return heap.getContent().entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey())||heapRefAddress.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    public void conservativeGarbageCollector(List<PrgState> programStates) {
        List<Integer> symTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromSymTable(p.getTopSymTable().values()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());
        List<Integer> heapTableAddresses = Objects.requireNonNull(programStates.stream()
                        .map(p -> getAddrFromHeapTable(p.getHeap().getAllValues()))
                        .map(Collection::stream)
                        .reduce(Stream::concat).orElse(null))
                .collect(Collectors.toList());

        programStates.forEach(p -> p.getHeap().setContent((HashMap<Integer, Value>) safeGarbageCollector(symTableAddresses, heapTableAddresses, p)));
    }
    public List<Integer> getAddrFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }
    public List<Integer> getAddrFromHeapTable(Collection<Value> heapTableValues){
        return heapTableValues.stream()
                .filter(v-> v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }
   public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
       return inPrgList.stream()
               .filter(p -> p.isNotCompleted())
               .collect(Collectors.toList());
    }
    void oneStepForAllPrg(List<PrgState> prgList) throws InterruptedException {
        //before the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });
        //RUN concurrently one step for each of the existing PrgStates
        //-----------------------------------------------------------------------
        //prepare the list of callables
        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());
        //start the execution of the callables
        //it returns the list of new created PrgStates (namely threads)
        List<PrgState> newPrgList = executor.invokeAll(callList). stream()
                . map(future -> { try { return future.get();}
                catch(InterruptedException | ExecutionException e) {
                  throw new RuntimeException(e.getMessage());}
                })
            .filter(p -> p!=null)
                            .collect(Collectors.toList());

        //add the new created threads to the list of existing threads
        prgList.addAll(newPrgList);
        //------------------------------------------------------------------------------

        //after the execution, print the PrgState List into the log file
        prgList.forEach(prg -> {
            try {
                repository.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });
        //Save the current programs in the repository
        repository.setPrgList(prgList);
    }
}
