package services;

import java.rmi.*;

public interface RemoteServiceInterface extends Remote{

    // The backup execution method
    public void backup(String path, int repDeg) throws RemoteException;

    // The restore execution method
    public void restore(String path) throws RemoteException;

    // The delete execution method
    public void delete(String path) throws RemoteException;

    // The reclaim execution method
    public void manageStorage(int kbs) throws RemoteException;
    
    // The state execution method
    public State state() throws RemoteException;

}
