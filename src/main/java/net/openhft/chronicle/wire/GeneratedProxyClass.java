package net.openhft.chronicle.wire;

import net.openhft.chronicle.core.Jvm;
import net.openhft.chronicle.core.io.Closeable;
import net.openhft.compiler.CompilerUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Created by Rob Austin
 *
 * The purpose of this class is to generate a proxy that will re-use the arg[]
 */
public enum GeneratedProxyClass {
    ;

    private static final String PACKAGE = "net.openhft.chronicle.wire.proxy";

    /**
     * @param interfaces an interface class
     * @return a proxy class from an interface class
     */
    public static Class from(Set<Class> interfaces, String className) {
        int maxArgs = 0;
        LinkedHashSet<Method> methods = new LinkedHashSet<Method>();

        StringBuilder sb = new StringBuilder("package " + PACKAGE + ";\n\n" +
                "import net.openhft.chronicle.core.Jvm;\n" +
                "import java.lang.reflect.InvocationHandler;\n" +
                "import java.lang.reflect.Method;\n" +
                "import java.util.ArrayList;\n" +
                "import java.util.List;\n");

        sb.append("public class " + className + " implements ");

        final Iterator<Class> iterator = interfaces.iterator();

        StringBuilder methodArray = new StringBuilder();
        int count = 0;

        // create methodArray
        while (iterator.hasNext()) {

            Class interfaceClazz = iterator.next();
            String interfaceName = interfaceClazz.getName().replace("$", ".");
            sb.append(interfaceName);

            if (!interfaceClazz.isInterface())
                throw new IllegalArgumentException("expecting and interface instead of class=" + interfaceClazz.getName());

            int j = -1;
            for (final Method dm : interfaceClazz.getMethods()) {
                j++;
                if (!methods.add(dm))
                    continue;
                maxArgs = Math.max(maxArgs, dm.getParameterCount());
                methodArray.append("    methods[" + (count++) + "]=" + interfaceClazz.getName().replace("$", ".") + ".class.getMethods()[" + j + "];\n");
            }

            if (!iterator.hasNext())
                break;

            sb.append(",\n              ");
        }
        sb.append(" {\n\n");

        addFieldsAndConstructor(maxArgs, methods, sb, className, methodArray);

        createProxyMethods(methods, sb);
        sb.append("}\n");

        try {
            return CompilerUtils.CACHED_COMPILER.loadFromJava(PACKAGE + "." + className, sb.toString());
        } catch (ClassNotFoundException e) {
            throw Jvm.rethrow(new ClassNotFoundException(e.getCause() + "\n" + sb.toString(), e));
        }

    }

    private static void addFieldsAndConstructor(final int maxArgs, final LinkedHashSet<Method> declaredMethods, final StringBuilder sb, final String className, final StringBuilder methodArray) {

        sb.append("  private final Object proxy;\n" +
                "  private final InvocationHandler handler;\n" +
                "  private Method[] methods = new  Method[" + declaredMethods.size() + "];\n");

        sb.append("  private List<Object[]> args = new ArrayList<Object[]>(" + (maxArgs + 1) + ");\n\n");

        sb.append("  public " + className + "(Object proxy, InvocationHandler handler) {\n" +
                "    this.proxy = proxy;\n" +
                "    this.handler = handler;\n");
        for (int j = 0; j <= maxArgs; j++) {
            sb.append("    args.add(new Object[" + j + "]);\n");
        }

        sb.append(methodArray);
        sb.append("  }\n\n");
    }

    private static void createProxyMethods(final LinkedHashSet<Method> declaredMethods, final StringBuilder sb) {
        int methodIndex = -1;
        for (final Method dm : declaredMethods) {

            final Class<?> returnType = dm.getReturnType();

            methodIndex++;

            sb.append(createMethodSignature(dm, returnType));

            sb.append("    Method method = this.methods[" + methodIndex + "];\n");
            sb.append("    Object[] a = this.args.get(" + dm.getParameterCount() + ");\n");

            assignParametersToArgs(sb, dm);
            callInvoke(sb, returnType);
        }
    }

    private static void callInvoke(final StringBuilder sb, final Class<?> returnType) {
        sb.append("    try {\n      ");

        if (returnType != void.class)
            sb.append("return (" + returnType.getName() + ")");
        sb.append(" handler.invoke(proxy,method,a);\n" +
                "    } catch (Throwable throwable) {\n" +
                "       throw Jvm.rethrow(throwable);\n" +
                "    }\n" +
                "  }\n");
    }

    private static void assignParametersToArgs(final StringBuilder sb, final Method dm) {
        final int len = dm.getParameters().length;
        for (int j = 0; j < len; j++) {
            String paramName = dm.getParameters()[j].getName();
            sb.append("    a[" + j + "] = " + paramName + ";\n");
        }
    }

    private static CharSequence createMethodSignature(final Method dm, final Class<?> returnType) {
        final int len = dm.getParameters().length;
        final StringBuilder result = new StringBuilder();

        result.append("  public ").append(returnType.getName() + " ").append(dm.getName()).append("(");

        for (int j = 0; j < len; j++) {
            Parameter p = dm.getParameters()[j];

            result.append(p.getType().getTypeName().replace("$", ".") + " " + p.getName());
            if (j == len - 1)
                break;

            result.append(",");
        }

        result.append(") {\n");

        return result;
    }

    public static String className(Class inter) {
        return inter.getName().replace(inter.getPackage().getName() + ".", "");
    }

    public static String className(Collection<Class> inter) {
        StringBuilder s = new StringBuilder();
        for (final Class aClass : inter) {
            s.append(className(aClass) + "$");
        }
        return s.toString();
    }

    public static interface MsgListener extends Closeable {
        void onMessage(Object o);
    }

}
