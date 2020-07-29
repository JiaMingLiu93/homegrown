package com.demo.middleware.canal.consumer;

import com.demo.middleware.canal.listener.SourceEventListener;
import com.demo.middleware.canal.message.StandardMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * This abstract class provides ability to process source event,which contains event filter
 * ,single/multi thread.
 *
 * The main template method to be implemented by subclasses is {@link #parse(String, Object)},
 * receiving a message and parsing to {@link StandardMessage} list.There is a implementation of
 * the operation is {@link CanalSourceEventConsumer}.
 *
 * @author youyu
 */
@Slf4j
public abstract class AbstractSourceEventConsumer<EVENT> implements SourceEventConsumer<EVENT>{

    private String filterDomainStr;
    private List<String> filterDomains;
    private SourceEventListener sourceEventListener;
    private Executor executor;

    /**
     * switch to consume source event
     */
    private Boolean close;
    /**
     * flag to use multi thread
     */
    private Boolean parallel;

    @PostConstruct
    public void init(){
        if (StringUtils.isEmpty(filterDomainStr)){
            log.warn("Empty filterDomains,all events will be effective!");
            return;
        }
        filterDomains = Arrays.asList(filterDomainStr.split(","));
        log.info("filterDomains are {}", filterDomains);
    }

    /**
     * Process source event with multi thread to offer high performance
     * of handling events.
     * @param database database of source event
     * @param event source event
     */
    @Override
    public void process(String database, EVENT event){
        if (close){
            records(database,event);
            return;

        }
        if (parallel){
            //todo
        }else {
            execute(database,event);
        }

    }

    @Override
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }

    private void execute(String database, EVENT event) {
        List<StandardMessage> messages = parse(database,event);
        if (!CollectionUtils.isEmpty(messages)){
            messages.forEach(message->{
                if (match(message.getDomainName())){
                    try {
                        sourceEventListener.onStandardMessage(message);
                    } catch (Exception e) {
                        log.error("failed to process the message,message={},error={}",message,e);
                    }
                }
            });
        }
    }

    private boolean match(String domainName) {
        if (CollectionUtils.isEmpty(filterDomains)){
            return true;
        }
        return filterDomains.contains(domainName);
    }

    /**
     * Parse source event to {@link StandardMessage} list.
     * @param database database of source event
     * @param event source event
     * @return com.demo.middleware.canal.message.StandardMessage
     */
    protected abstract List<StandardMessage> parse(String database, EVENT event);

    /**
     * Record source event when skip.
     * <p>There are some ways to record event that is received but not consume,
     * such as log,persistence and so on.
     * @param database database of source event
     * @param event source event
     */
    protected abstract void records(String database, EVENT event);
}