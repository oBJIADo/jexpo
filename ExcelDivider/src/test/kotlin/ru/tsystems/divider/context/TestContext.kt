package ru.tsystems.divider.context

import ru.tsystems.divider.service.api.functional.CommentBuilder
import ru.tsystems.divider.service.api.functional.DataService
import ru.tsystems.divider.service.api.functional.EmployeeBuilder
import ru.tsystems.divider.service.api.functional.EntityBuilder
import ru.tsystems.divider.service.api.functional.FeatureBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.service.api.functional.RowToEntityConverter
import ru.tsystems.divider.service.impl.functional.CommentBuilderImpl
import ru.tsystems.divider.service.impl.functional.DataServiceImpl
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


    var dataService: DataService
        private set

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

        featureBuilder = FeatureBuilderImpl(
            messageWorker = messageWorker,
            dataService = dataService
        )

        employeeBuilder = EmployeeBuilderImpl(
            messageWorker = messageWorker,
            dataService = dataService
        )

        commentBuilder = CommentBuilderImpl(
            messageWorker = messageWorker,
            employeeBuilder = employeeBuilder,
            dataService = dataService
        )


        entityBuilder = EntityBuilderImpl(
            messageWorker = MessageSimulator,
            fieldBuilder = fieldBuilder,
            featureBuilder = featureBuilder,
            employeeBuilder = employeeBuilder,
            dataService = dataService,
            commentBuilder = commentBuilder
        )

        rteConv = RowToEntityConverterImpl(
            messageWorker = messageWorker,
            builder = entityBuilder,
            fieldBuilder = fieldBuilder,
            dataService = dataService
        )
    }

    /**
     * If you change MessageWorkerSimmulator map or change properties, you should rebuild some functional services.
     * In real use case properties do not changes.
     */
    fun rebuildServices() {
        fieldBuilder = FieldBuilderImpl()
        entityBuilder = EntityBuilderImpl(
            messageWorker = MessageSimulator,
            fieldBuilder = fieldBuilder,
            featureBuilder = featureBuilder,
            employeeBuilder = employeeBuilder,
            dataService = dataService,
            commentBuilder = commentBuilder
        )

        rteConv = RowToEntityConverterImpl(
            messageWorker = messageWorker,
            builder = entityBuilder,
            fieldBuilder = fieldBuilder,
            dataService = dataService
        )
    }

    init {
        messageWorker = MessageSimulator

        taskDao = InMemoryTask
        featureDao = InMemoryFeature
        natureDao = InMemoryNature
        employeeDao = InMemoryEmployee
        commentDao = InMemoryComment

        dataService = DataServiceImpl(
            taskDao = taskDao,
            employeeDao = employeeDao,
            featureDao = featureDao,
            natureDao = natureDao,
            commentDao = commentDao
        )

        featureBuilder = FeatureBuilderImpl(
            messageWorker = messageWorker,
            dataService = dataService
        )
        employeeBuilder = EmployeeBuilderImpl(
            messageWorker = messageWorker,
            dataService = dataService
        )

        commentBuilder = CommentBuilderImpl(
            messageWorker = messageWorker,
            employeeBuilder = employeeBuilder,
            dataService = dataService
        )

        fieldBuilder = FieldBuilderImpl()

        entityBuilder = EntityBuilderImpl(
            messageWorker = MessageSimulator,
            fieldBuilder = fieldBuilder,
            featureBuilder = featureBuilder,
            employeeBuilder = employeeBuilder,
            dataService = dataService,
            commentBuilder = commentBuilder
        )

        rteConv = RowToEntityConverterImpl(
            messageWorker = MessageSimulator,
            builder = entityBuilder,
            fieldBuilder = fieldBuilder,
            dataService = dataService
        )
    }
}