
package com.kenai.jaffl.provider.jffi;

import com.kenai.jaffl.LibraryOption;
import com.kenai.jaffl.provider.MemoryManager;
import com.kenai.jffi.LastError;
import java.util.Map;


public class Provider extends com.kenai.jaffl.FFIProvider {
    private final MemoryManager memoryManager = new com.kenai.jaffl.provider.jffi.MemoryManager();
    @Override
    public MemoryManager getMemoryManager() {
        return memoryManager;
    }

    @Override
    public <T> T loadLibrary(String libraryName, Class<T> interfaceClass, Map<LibraryOption, ?> libraryOptions) {
        return loadLibrary(new Library(libraryName), interfaceClass, libraryOptions);
    }

    @Override
    public <T> T loadLibrary(Class<T> interfaceClass, Map<LibraryOption, ?> libraryOptions, String... libraryNames) {
        return loadLibrary(new Library(libraryNames), interfaceClass, libraryOptions);
    }

    private <T> T loadLibrary(Library library, Class<T> interfaceClass, Map<LibraryOption, ?> libraryOptions) {
        try {
            if (AsmLibraryLoader.getInstance().isInterfaceSupported(interfaceClass, libraryOptions)) {
                return AsmLibraryLoader.getInstance().loadLibrary(library, interfaceClass, libraryOptions);
            }
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
        
        return ProxyLibraryLoader.getInstance().loadLibrary(library, interfaceClass, libraryOptions);
    }
    
    @Override
    public int getLastError() {
        return LastError.getInstance().get();
    }

    @Override
    public void setLastError(int error) {
        LastError.getInstance().set(error);
    }

}