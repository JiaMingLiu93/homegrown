package com.homegrown.tools.code.generator.struct.processor.demo.processor;

import com.homegrown.tools.code.generator.struct.processor.demo.annotation.*;
import com.homegrown.tools.code.generator.struct.processor.demo.builder.ProcessorContextBuilder;
import com.homegrown.tools.code.generator.struct.processor.demo.utils.Strings;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementKindVisitor6;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.util.*;
import java.util.stream.Collectors;

import com.homegrown.tools.code.generator.struct.processor.demo.processor.ElementProcessor.ProcessorContext;

/**
 * @author youyu
 */
@SupportedAnnotationTypes("com.homegrown.tools.code.generator.struct.processor.demo.annotation.GeneratorConfig")
public class ConfigurationProcessor extends AbstractProcessor {

    public static final int FIRST_ROUND = 1;
    public static final String SUPER_CLASS_NAME = "SuperClassName";

    private AnnotationProcessorContext annotationProcessorContext;
    private int counter;

    public TypeElement getFacadeTypeElement() {
        return facadeTypeElement;
    }

    private TypeElement facadeTypeElement;
    private Map<String,TypeElement> existedTypeElements = new HashMap<>();
    private ServiceTypeConfig serviceTypeConfig;
    private boolean isServiceProcessed;
    //cache configs
    private Map<GenerateTypeEnum, AnnotationMapping> configs = new HashMap<>();
    private Set<GenerateTypeEnum> configRecords = new HashSet<>();
    private Set<? extends Element> rootElements;
    private Element configElement;

    private List<ProcessorContextBuilder> processorContextBuilders;
    private ListIterator<ProcessorContextBuilder> iterator;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init( processingEnv );

        annotationProcessorContext = new AnnotationProcessorContext(
                processingEnv.getElementUtils(),
                processingEnv.getTypeUtils()
        );

        processorContextBuilders = getProcessorContextGenerators();
        iterator = processorContextBuilders.listIterator();

        //init for the first round
        counter = 1;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        System.out.println("user.dir="+System.getProperty("user.dir"));

        //run in the Intellij environment,not the executable jar
        String args = ManagementFactory.
                getRuntimeMXBean().
                getInputArguments().toString();
        if (!args.contains("IntelliJ")){
            return false;
        }

            rootElements = roundEnv.getRootElements();

        if (CollectionUtils.isNotEmpty(annotations)){
            configElement = getConfigElement(annotations, roundEnv);
            GeneratorConfig generatorConfig = configElement.getAnnotation(GeneratorConfig.class);

            if (generatorConfig == null){
                System.out.println("no GeneratorConfig.");
                return false;
            }

            configs.putIfAbsent(GenerateTypeEnum.GENERAL,AnnotationMapping.from(generatorConfig));
            catchAndCacheTypeElement(generatorConfig.model());
        }
        //if it is first round and annotations are empty then skip
        else if (counter == 1){
            return false;
        }

        //test

        //init facadeTypeElement
//        if (facadeTypeElement == null){
//
//            Optional<TypeElement> facadeTypeElementOption = rootElements.stream().map(e -> {
//                if (e.getSimpleName().toString().endsWith("Facade") && e.getSimpleName().toString().contains("TestFacade")) {
//                    TypeElement element = asTypeElement(e);
//                    return element;
//                }
//                return null;
//            }).filter(Objects::nonNull).findFirst();
//
//            if (!facadeTypeElementOption.isPresent()){
//                return false;
//            }
//            facadeTypeElement = facadeTypeElementOption.get();
//        }
        //test



        List<TypeConfig> typeConfigs = Arrays.asList(configElement.getAnnotationsByType(TypeConfig.class));
        if (CollectionUtils.isEmpty(typeConfigs)){
            System.out.println("no TypeConfigs.");
            return false;
        }
        typeConfigs.forEach(typeConfig -> configs.putIfAbsent(typeConfig.type(),AnnotationMapping.from(typeConfig)));

        //do sth for the pre round,for example caching generated typeElement
        if (iterator.hasPrevious()){
            ProcessorContextBuilder previous = iterator.previous();
            previous.postAfter();
        }

        //generate source code through processor
        while (iterator.hasNext()){
            ProcessorContextBuilder processorContextBuilder = iterator.next();

            ProcessorContext processorContext = processorContextBuilder.build(this);
            if (processorContext != null){
                processAvailableElements(processorContext);
                counter++;
                break;
            }
        }

        return true;
    }

    private List<ProcessorContextBuilder> getProcessorContextGenerators() {
        Iterator<ProcessorContextBuilder> iterator = ServiceLoader.load(ProcessorContextBuilder.class, ConfigurationProcessor.class.getClassLoader()).iterator();
        List<ProcessorContextBuilder> generators = new ArrayList<>();
        while (iterator.hasNext()){
            generators.add(iterator.next());
        }
        generators.sort(new ProcessorContextGeneratorComparator());

        return generators;
    }

    private String getRequestClassName(GeneratorConfig generatorConfig, RequestTypeConfig requestTypeConfig) {
        String requestClassName = requestTypeConfig.className();
        if (Strings.isEmpty(requestClassName)){
            requestClassName = getDefaultRequestClassName(generatorConfig);
        }
        return requestClassName;
    }

    private boolean checkClassExist(String classKey) {
        return existedTypeElements.containsKey(classKey);
    }

    private String getDefaultRequestClassName(GeneratorConfig generatorConfig) {
        return upperCase(generatorConfig.model())+upperCase(generatorConfig.methodName())+"Request";
    }

    /**
     * cache necessary TypeElement
     * @param className must be qualified name
     */
    public void catchAndCacheTypeElement(String className) {
        String simpleClassName = getSimpleClassName(className);
        String packageName = className.substring(0, className.lastIndexOf('.'));

        Optional<? extends Element> optionalElement = rootElements.stream()
                .filter(element -> element.getSimpleName().contentEquals(simpleClassName))
                .filter(element -> {
                    PackageElement packageElement = getProcessingEnv().getElementUtils().getPackageOf(element);
                    return packageElement.getQualifiedName().contentEquals(packageName);
                })
                .findFirst();
        if (!optionalElement.isPresent()){
            System.out.println("failed to find TypeElement of: "+ className);
        }
        optionalElement.ifPresent(element -> existedTypeElements.putIfAbsent(className,asTypeElement(element)));
    }

    /**
     * cache necessary TypeElement
     * @param classNames must be qualified name
     */
    public void catchAndCacheTypeElements(Collection<String> classNames){
        if (CollectionUtils.isNotEmpty(classNames)){
            classNames.forEach(this::catchAndCacheTypeElement);
        }
    }

    private void cacheDefaultRepoTypeElement(GeneratorConfig generatorConfig, Set<? extends Element> rootElements) {
        Optional<? extends Element> repoElementOpt = rootElements.stream().filter(element -> element.getSimpleName().contentEquals(getDefaultRepoClassName(generatorConfig))).findFirst();

        repoElementOpt.ifPresent(element -> existedTypeElements.put("repo", asTypeElement(element)));
    }

    private String getDefaultRepoClassName(GeneratorConfig generatorConfig) {
        return generatorConfig.model()+"Dao";
    }

    private String getPath(String packageName) {
        String repoPath = packageName.replace(".", "/");
        return packageName + "/" + "src/main/java/" + repoPath;
    }

    private void processAvailableElements(ProcessorContext processorContext) {
        if (processorContext == null){
            return;
        }
        List<ElementProcessor<?, ?>> processors = getProcessors();

        Object sourceModel = null;

        for (ElementProcessor<?,?> processor : processors){
           sourceModel = process(processorContext,processor,processorContext.getTemplateTypeElement(),sourceModel);
        }
    }

    private <P, R> R process(ProcessorContext context, ElementProcessor<P, R> processor,
                             TypeElement mapperTypeElement, Object modelElement) {
        @SuppressWarnings("unchecked")
        P sourceElement = (P) modelElement;
        return processor.process( context, mapperTypeElement, sourceElement );
    }

    private List<ElementProcessor<?,?>> getProcessors(){
        @SuppressWarnings("rawtypes")
        Iterator<ElementProcessor> iterator = ServiceLoader.load(ElementProcessor.class, ConfigurationProcessor.class.getClassLoader()).iterator();
        List<ElementProcessor<?,?>> elementProcessors = new ArrayList<>();

        while (iterator.hasNext()){
            elementProcessors.add(iterator.next());
        }

        elementProcessors.sort(new ProcessorComparator());

        return elementProcessors;
    }

    private Set<TypeElement> getAvailableTypes(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Element configElement = getConfigElement(annotations, roundEnv);

        GeneratorConfig generatorConfig = configElement.getAnnotation(GeneratorConfig.class);
        String rootPath = System.getProperty("user.dir");

        if (counter == FIRST_ROUND){
            String repoPath = generatorConfig.repoPackage();
            repoPath = repoPath.replace(".", "/");

            String model = generatorConfig.model();

            repoPath = rootPath+"/"+"src/main/java/"+repoPath+"/"+model+"Dao.java";

            System.out.println(repoPath);

            File repoFile = new File(repoPath);

            if (repoFile.exists()){
                return Collections.emptySet();
            }

            //generate

        }

        String matchSuffix = generatorConfig.matchSuffix();
        Set<? extends Element> rootElements = roundEnv.getRootElements();

        return rootElements.stream().map(e -> {
            if (e.getSimpleName().toString().endsWith(matchSuffix)) {
                return asTypeElement(e);
            }
            return null;
        }).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    private Element getConfigElement(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Element configElement = null;
        for (TypeElement annotation: annotations){
            Set<? extends Element> configElements = roundEnv.getElementsAnnotatedWith(annotation);
            configElement = configElements.stream().findFirst().get();
        }
        return configElement;
    }

    private TypeElement asTypeElement(Element element) {
        return element.accept(
                new ElementKindVisitor6<TypeElement, Void>() {
                    @Override
                    public TypeElement visitTypeAsInterface(TypeElement e, Void p) {
                        return e;
                    }

                    @Override
                    public TypeElement visitTypeAsClass(TypeElement e, Void p) {
                        return e;
                    }

                }, null
        );
    }

    public RoundContext getRoundContext() {
        return new RoundContext(annotationProcessorContext);
    }

    public String getSimpleClassName(String className) {
        return className.substring(className.lastIndexOf('.') + 1);
    }

    private static class ProcessorComparator implements Comparator<ElementProcessor<?, ?>> {
        @Override
        public int compare(ElementProcessor<?, ?> o1, ElementProcessor<?, ?> o2) {
            return Integer.compare( o1.getPriority(), o2.getPriority() );
        }
    }

    private static class ProcessorContextGeneratorComparator implements Comparator<ProcessorContextBuilder> {
        @Override
        public int compare(ProcessorContextBuilder o1, ProcessorContextBuilder o2) {
            return Integer.compare( o1.getPriority(), o2.getPriority() );
        }
    }

    public String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    public Map<GenerateTypeEnum, AnnotationMapping> getConfigs() {
        return configs;
    }

    public ProcessingEnvironment getProcessingEnv(){
        return processingEnv;
    }

    public AnnotationMapping getGeneratorConfig(){
        return configs.get(GenerateTypeEnum.GENERAL);
    }

    public Map<String, TypeElement> getExistedTypeElements() {
        return existedTypeElements;
    }

    public Set<? extends Element> getRootElements() {
        return rootElements;
    }

    public Element getConfigElement() {
        return configElement;
    }

    public String getPrePath(){
        return System.getProperty("user.dir")+"/" + "src/main/java/";
    }

    public String getSuperClassKey(AnnotationMapping annotation){
        return annotation.getPackageName()+ SUPER_CLASS_NAME;
    }

    public Set<GenerateTypeEnum> getConfigRecords() {
        return configRecords;
    }
}
