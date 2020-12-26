package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import com.homegrown.tools.code.generator.struct.processor.demo.model.GeneralSource;
import com.homegrown.tools.code.generator.struct.processor.demo.utils.compile.Compile;
import com.homegrown.tools.code.generator.struct.processor.demo.utils.compile.CompileOptions;
import com.homegrown.tools.code.generator.struct.processor.demo.writer.ModelWriter;

import javax.annotation.processing.Filer;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.JavaFileObject;
import javax.tools.StandardLocation;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;

/**
 * @author youyu
 */
public class TypeRenderingElementProcessor implements ElementProcessor<GeneralSource, GeneralSource> {
    @Override
    public GeneralSource process(ProcessorContext context, TypeElement typeElement, GeneralSource sourceModel) {
        if (!context.isErroneous()){
            File file;
            ModelWriter modelWriter = new ModelWriter();


            FileObject sourceFile;
            try {
                file = new File(context.getPath());
                if (!file.exists()){
                    Files.createDirectory(file.toPath());
                }
                File modelFile = new File(file.getAbsolutePath() + "/" + context.getSimpleClassName() + ".java");

                if (modelFile.exists()){
                    return sourceModel;
                }

                Filer filer = context.getFiler();
                sourceFile = filer.createSourceFile(context.getPackageName()+"."+context.getSimpleClassName());
            }
            catch ( Exception e ) {
                throw new RuntimeException( e );
            }

            modelWriter.writeModel( sourceFile, sourceModel );
        }
        return sourceModel;
    }

    @Override
    public int getPriority() {
        return 9999;
    }
}
