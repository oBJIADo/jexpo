package ru.tsystems.divider.context

import ru.tsystems.divider.service.api.functional.CommentBuilder
import ru.tsystems.divider.service.api.functional.EmployeeBuilder
import ru.tsystems.divider.service.api.functional.EntityBuilder
import ru.tsystems.divider.service.api.functional.FeatureBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.service.api.functional.RowToEntityConverter
import ru.tsystems.divider.service.impl.functional.CommentBuilderImpl
import ru.tsystems.divider.service.impl.functional.EmployeeBuilderImpl
import ru.tsystems.divider.service.impl.functional.EntityBuilderImpl
import ru.tsystems.divider.service.impl.functional.FeatureBuilderImpl
import ru.tsystems.divider.service.impl.functional.FieldBuilderImpl
import ru.tsystems.divider.service.impl.functional.RowToEntityConverterImpl
import ru.tsystems.divider.utils.api.MessageWorker

object TestContext {

    private val messageWorker: MessageWorker

    private val taskDao: InMemoryTask
    private val featureDao: InMemoryFeature
    private val natureDao: InMemoryNature
    private val employeeDao: InMemoryEmployee
    private val commentDao: InMemoryComment


    var entityBuilder: EntityBuilder
        private set

    var featureBuilder: FeatureBuilder
        private set

    var employeeBuilder: EmployeeBuilder
        private set

    var commentBuilder: CommentBuilder
        private set

    var fieldBuilder: FieldBuilder
        private set

    var rteConv: RowToEntityConverter
        private set

    fun reset() {
        MessageSimulator.reset()
        InMemoryFeature.reset()
        InMemoryNature.reset()
        InMemoryEmployee.reset()
        InMemoryComment.reset()
        InMemoryTask.reset()

        featureBuilder =
                FeatureBuilderImpl(messageWorker = messageWorker, featureDao = featureDao, natureDao = natureDao)
        employeeBuilder = EmployeeBuilderImpl(messageWorker = messageWorker, dao = employeeDao)
        commentBuilder =
                CommentBuilderImpl(messageWorker = messageWorker, employeeBuilder = employeeBuilder, taskDao = taskDao)


        entityBuilder =
                EntityBuilderImpl(
                    MessageSimulator,
                    fieldBuilder,
                    featureBuilder,
                    employeeBuilder,
                    InMemoryTask,
                    commentBuilder
                )

        rteConv = RowToEntityConverterImpl(messageWorker, entityBuilder, fieldBuilder, taskDao, commentDao)
    }

    /**
     * If you change MessageWorkerSimmulator map or change properties, you should rebuild some functional services.
     * In real use case properties do not changes.
     */
    fun rebuildServices() {
        fieldBuilder = FieldBuilderImpl()
        entityBuilder =
                EntityBuilderImpl(
                    MessageSimulator,
                    fieldBuilder,
                    featureBuilder,
                    employeeBuilder,
                    InMemoryTask,
                    commentBuilder
                )

        rteConv = RowToEntityConverterImpl(messageWorker, entityBuilder, fieldBuilder, taskDao, commentDao)
    }

    init {
        messageWorker = MessageSimulator

        taskDao = InMemoryTask
        featureDao = InMemoryFeature
        natureDao = InMemoryNature
        employeeDao = InMemoryEmployee
        commentDao = InMemoryComment

        featureBuilder =
                FeatureBuilderImpl(messageWorker = messageWorker, featureDao = featureDao, natureDao = natureDao)
        employeeBuilder = EmployeeBuilderImpl(messageWorker = messageWorker, dao = employeeDao)
        commentBuilder =
                CommentBuilderImpl(messageWorker = messageWorker, employeeBuilder = employeeBuilder, taskDao = taskDao)

        fieldBuilder = FieldBuilderImpl()
        entityBuilder =
                EntityBuilderImpl(
                    MessageSimulator,
                    fieldBuilder,
                    featureBuilder,
                    employeeBuilder,
                    InMemoryTask,
                    commentBuilder
                )

        rteConv = RowToEntityConverterImpl(MessageSimulator, entityBuilder, fieldBuilder, InMemoryTask, InMemoryComment)
    }
}