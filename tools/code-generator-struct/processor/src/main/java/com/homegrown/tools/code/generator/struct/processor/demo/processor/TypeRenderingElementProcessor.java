package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import com.homegrown.tools.code.generator.struct.processor.demo.model.GeneralSource;
import com.homegrown.tools.code.generator.struct.processor.demo.writer.ModelWriter;

import javax.lang.model.element.TypeElement;
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
            ModelWriter modelWriter = new ModelWriter();

            FileWriter sourceFile;
            try {
                File file = new File(context.getPath());
                if (!file.exists()){
                    Files.createDirectory(file.toPath());
                }
                File modelFile = new File(file.getAbsolutePath() + "/" + context.getSimpleClassName() + ".java");

                if (modelFile.exists()){
                    return sourceModel;
                }

                modelFile.createNewFile();

                sourceFile = new FileWriter(modelFile);
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
