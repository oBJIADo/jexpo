package ru.tsystems.divider.context;


import ru.tsystems.divider.components.api.MessageWorker;
import ru.tsystems.divider.service.api.EntityBuilder;
import ru.tsystems.divider.service.api.FeatureService;
import ru.tsystems.divider.service.api.FieldBuilder;
import ru.tsystems.divider.service.api.JiraToDBConverter;
import ru.tsystems.divider.service.api.NatureService;
import ru.tsystems.divider.service.api.RowToEntityConverter;
import ru.tsystems.divider.service.impl.EntityBuilderImpl;
import ru.tsystems.divider.service.impl.FeatureServiceImpl;
import ru.tsystems.divider.service.impl.FieldBuilderImpl;
import ru.tsystems.divider.service.impl.JiraToDBConverterImpl;
import ru.tsystems.divider.service.impl.NatureServiceImpl;
import ru.tsystems.divider.service.impl.RowToEntityConverterImpl;

public class TestContext {

    private static TestContext testContext;

    public static TestContext getTestContext() {
        if (testContext == null) {
            testContext = new TestContext();
        }
        return testContext;
    }

    private final MessageWorker messageWorker;

    private final InMemoryTask taskDao;
    private final InMemoryFeature featureDao;
    private final InMemoryNature natureDao;
    private final InMemoryEmployee employeeDao;
    private final InMemoryComment commentDao;

    private final NatureService natureService;
    private final FeatureService featureService;

    private final EntityBuilder entityBuilder;
    private final FieldBuilder fieldBuilder;

    private final JiraToDBConverter jtbConv;
    private final RowToEntityConverter rteConv;

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
        } catch (Exception e){
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
