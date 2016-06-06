package OOP.Solution;
import OOP.Provided.OOPMultipleException;
public class OOPMultiple implements OOP.Tests.NivTests.Interfaces.I42 { 

private OOPMultipleControl dispatcher;


public OOPMultiple(OOPMultipleControl dispatcher){
this.dispatcher = dispatcher;
}

 
public void fOverridenPrivate () throws OOPMultipleException {dispatcher.invoke("fOverridenPrivate", null);
}
 
public void fOverridenProtected () throws OOPMultipleException {dispatcher.invoke("fOverridenProtected", null);
}
 
public void fOverridendefault () throws OOPMultipleException {dispatcher.invoke("fOverridendefault", null);
}
 
public void fPrivate () throws OOPMultipleException {dispatcher.invoke("fPrivate", null);
}
 
public void fdefault () throws OOPMultipleException {dispatcher.invoke("fdefault", null);
}
 
public void fProtected () throws OOPMultipleException {dispatcher.invoke("fProtected", null);
}
 
public void fPublic () throws OOPMultipleException {dispatcher.invoke("fPublic", null);
}
 
public void fDefaultInSamePackage () throws OOPMultipleException {dispatcher.invoke("fDefaultInSamePackage", null);
}
 }