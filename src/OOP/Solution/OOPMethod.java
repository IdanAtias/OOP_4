package OOP.Solution;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

@Target(ElementType.METHOD) // to be used just for methods.
@Retention(RetentionPolicy.RUNTIME) // make it valid on runtime.
public @interface OOPMethod {
	OOPModifier modifier() default OOPModifier.DEFAULT;
}
