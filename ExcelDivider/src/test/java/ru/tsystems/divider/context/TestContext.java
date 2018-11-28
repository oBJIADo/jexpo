package ru.tsystems.divider.context;


import ru.tsystems.divider.service.api.entity.FeatureService;
import ru.tsystems.divider.service.api.entity.NatureService;
import ru.tsystems.divider.service.api.functional.EntityBuilder;
import ru.tsystems.divider.service.api.functional.FieldBuilder;
import ru.tsystems.divider.service.api.functional.JiraToDBConverter;
import ru.tsystems.divider.service.api.functional.RowToEntityConverter;
import ru.tsystems.divider.service.impl.entity.FeatureServiceImpl;
import ru.tsystems.divider.service.impl.entity.NatureServiceImpl;
import ru.tsystems.divider.service.impl.functional.EntityBuilderImpl;
import ru.tsystems.divider.service.impl.functional.FieldBuilderImpl;
import ru.tsystems.divider.service.impl.functional.JiraToDBConverterImpl;
import ru.tsystems.divider.service.impl.functional.RowToEntityConverterImpl;
import ru.tsystems.divider.utils.api.MessageWorker;

public class TestContext {

    private static TestContext testContext;

    public static TestContext getTestContext() {
        if (testContext == null) {
            testContext = new TestContext();
        }
        return testContext;
    }

    public void reset() {
        ((ContextSimmulator) messageWorker).reset();
        ((ContextSimmulator) taskDao).reset();
        ((ContextSimmulator) featureDao).reset();
        ((ContextSimmulator) natureDao).reset();
        ((ContextSimmulator) employeeDao).reset();
        ((ContextSimmulator) commentDao).reset();

        testContext = new TestContext();
    }

    /**
     * If you change MessageWorkerSimmulator map or change properties, you should rebuild some functional services.
     * In real use case properties do not changes.
     */
    public void rebuildServices() {
        try {
            fieldBuilder = new FieldBuilderImpl(messageWorker);
            entityBuilder = new EntityBuilderImpl(messageWorker, fieldBuilder, featureService, employeeDao, taskDao);

            rteConv = new RowToEntityConverterImpl(messageWorker, entityBuilder, fieldBuilder, taskDao, commentDao);
        } catch (Exception e) {
            throw new IllegalArgumentException("IDK"); //todo: crutch
        }
    }

    private MessageWorker messageWorker;

    private final InMemoryTask taskDao;
    private final InMemoryFeature featureDao;
    private final InMemoryNature natureDao;
    private final InMemoryEmployee employeeDao;
    private final InMemoryComment commentDao;

    private final NatureService natureService;
    private final FeatureService featureService;

    private EntityBuilder entityBuilder;
    private FieldBuilder fieldBuilder;

    private RowToEntityConverter rteConv;
    private final JiraToDBConverter jtbConv;

    private TestContext() {
        try {
            messageWorker = MessageSimulator.getMessageWorker();

            taskDao = InMemoryTask.getInMemoryDao();
            featureDao = InMemoryFeature.getInMemoryDao();
            natureDao = InMemoryNature.getInMemoryDao();
            employeeDao = InMemoryEmployee.getInMemoryDao();
            commentDao = InMemoryComment.getInMemoryDao();

            natureService = new NatureServiceImpl(natureDao);
            featureService = new FeatureServiceImpl(featureDao, natureService);

            fieldBuilder = new FieldBuilderImpl(messageWorker);
            entityBuilder = new EntityBuilderImpl(messageWorker, fieldBuilder, featureService, employeeDao, taskDao);

            rteConv = new RowToEntityConverterImpl(messageWorker, entityBuilder, fieldBuilder, taskDao, commentDao);
            jtbConv = new JiraToDBConverterImpl(rteConv);
        } catch (Exception e) {
            throw new IllegalArgumentException("IDK"); //todo: crutch
        }
    }

    public MessageWorker getMessageWorker() {
        return messageWorker;
    }

    public InMemoryTask getTaskDao() {
        return taskDao;
    }

    public InMemoryFeature getFeatureDao() {
        return featureDao;
    }

    public InMemoryNature getNatureDao() {
        return natureDao;
    }

    public InMemoryEmployee getEmployeeDao() {
        return employeeDao;
    }

    public InMemoryComment getCommentDao() {
        return commentDao;
    }

    public NatureService getNatureService() {
        return natureService;
    }

    public FeatureService getFeatureService() {
        return featureService;
    }

    public EntityBuilder getEntityBuilder() {
        return entityBuilder;
    }

    public FieldBuilder getFieldBuilder() {
        return fieldBuilder;
    }

    public JiraToDBConverter getJtbConv() {
        return jtbConv;
    }

    public RowToEntityConverter getRteConv() {
        return rteConv;
    }
}
