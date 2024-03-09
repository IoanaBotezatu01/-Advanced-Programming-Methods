package repository;

import model.ADT.MyIStack;
import model.MyException;
import model.PrgState;
import model.stmt.IStmt;

import java.util.List;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Repository implements IRepository{
    private List<PrgState> prgList;
    private String logFilePath;
    public Repository(List<PrgState> p, String givenLogFilePath) {
        prgList = p;
        logFilePath = givenLogFilePath;
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.prgList;
    }

    @Override
    public void setPrgList(List<PrgState> prgL) {
        this.prgList=prgL;
    }

   /* @Override
    public PrgState getCrtPrg() {
        return this.prgList.get(0);
    }*/
    @Override
    public void addPrg(PrgState prg) {
        this.prgList.add(prg);
    }

    @Override
    public void logPrgStateExec(PrgState program) throws MyException {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
            logFile.println("******************************");
            logFile.println("~~~~~ExeStack~~~~~~");
            logFile.println(program.getExeStack().toString());
            logFile.println("~~~~~~SymbolTable~~~~~~");
            logFile.println(program.getSymTable().toString());
            logFile.println("~~~~~~Output~~~~~~");
            logFile.println(program.getOut().toString());
            logFile.println("~~~~~~FileTable~~~~~");
            logFile.println(program.getFileTable().toString());
            logFile.println("~~~~~~Heap~~~~~");
            logFile.println(program.getHeap().toString());
            logFile.println("~~~~~~Id~~~~~");
            logFile.println(program.getId());
            logFile.println("******************************");
            logFile.println("\n");
            logFile.close();
        } catch (IOException e) {
            throw new MyException("Error writing to log file");
        }
    }


}
