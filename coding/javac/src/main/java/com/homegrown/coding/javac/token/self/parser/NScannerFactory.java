package com.homegrown.coding.javac.token.self.parser;

import com.sun.tools.javac.code.Source;
import com.sun.tools.javac.parser.ScannerFactory;
import com.sun.tools.javac.parser.Tokens;
import com.sun.tools.javac.util.Context;
import com.sun.tools.javac.util.Log;
import com.sun.tools.javac.util.Names;

/**
 * A factory for creating scanners.
 * @author jam
 * @description
 * @create 2019-09-06
 **/
public class NScannerFactory {
    public static final Context.Key<NScannerFactory> scannerFactoryKey =
            new Context.Key<>();

    /** Get the Factory instance for this context. */
    public static NScannerFactory instance(Context context) {
        NScannerFactory instance = context.get(scannerFactoryKey);
        if (instance == null)
            instance = new NScannerFactory(context);
        return instance;
    }

    final Log log;
    final Names names;
    final Source source;
    final Tokens tokens;

    /** Create a new scanner factory. */
    protected NScannerFactory(Context context) {
        context.put(scannerFactoryKey, this);
        this.log = Log.instance(context);
        this.names = Names.instance(context);
        this.source = Source.instance(context);
        this.tokens = Tokens.instance(context);
    }


}
