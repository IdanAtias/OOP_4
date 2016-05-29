package OOP.Solution;

import OOP.Provided.*;
import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class OOPMultipleControl {

    //TODO: DO NOT CHANGE !!!!!!
    private Class<?> interfaceClass;
    private File sourceFile;

    //TODO: DO NOT CHANGE !!!!!!
    public OOPMultipleControl(Class<?> interfaceClass, File sourceFile) {
        this.interfaceClass = interfaceClass;
        this.sourceFile = sourceFile;
    }

    //TODO: fill in here :
    public void validateInheritanceTree() throws OOPMultipleException {

    }

    //TODO: fill in here :
    public Object invoke(String methodName, Object[] args)
            throws OOPMultipleException {

        return null;
    }

    //TODO: add more of your code :
    public enum OOPModifier{
    	DEFAULT, PRIVATE, PROTECTED, PUBLIC
    }
    
    @Target(ElementType.METHOD)	// to be used just for methods.
    @Retention(RetentionPolicy.RUNTIME) //make it valid on runtime.
    public @interface OOPMethod {
    	OOPModifier modifier() default OOPModifier.DEFAULT;
    }
    


    //TODO: DO NOT CHANGE !!!!!!
    public void removeSourceFile() {
        if (sourceFile.exists()) {
            sourceFile.delete();
        }
    }
}
