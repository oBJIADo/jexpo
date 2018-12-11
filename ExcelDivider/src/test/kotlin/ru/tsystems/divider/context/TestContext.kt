package ru.tsystems.divider.context

import ru.tsystems.divider.service.api.entity.FeatureService
import ru.tsystems.divider.service.api.entity.NatureService
import ru.tsystems.divider.service.api.functional.EntityBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.service.api.functional.RowToEntityConverter
import ru.tsystems.divider.service.impl.entity.FeatureServiceImpl
import ru.tsystems.divider.service.impl.entity.NatureServiceImpl
import ru.tsystems.divider.service.impl.functional.EntityBuilderImpl
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

    private val natureService: NatureService
    private val featureService: FeatureService

    var entityBuilder: EntityBuilder
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

        fieldBuilder = FieldBuilderImpl(messageWorker)
        entityBuilder = EntityBuilderImpl(messageWorker, fieldBuilder, featureService, employeeDao, taskDao)

        rteConv = RowToEntityConverterImpl(messageWorker, entityBuilder, fieldBuilder, taskDao, commentDao)
    }

    /**
     * If you change MessageWorkerSimmulator map or change properties, you should rebuild some functional services.
     * In real use case properties do not changes.
     */
    fun rebuildServices() {
        fieldBuilder = FieldBuilderImpl(messageWorker)
        entityBuilder = EntityBuilderImpl(messageWorker, fieldBuilder, featureService, employeeDao, taskDao)

        rteConv = RowToEntityConverterImpl(messageWorker, entityBuilder, fieldBuilder, taskDao, commentDao)
    }

    init {
        messageWorker = MessageSimulator

        taskDao = InMemoryTask
        featureDao = InMemoryFeature
        natureDao = InMemoryNature
        employeeDao = InMemoryEmployee
        commentDao = InMemoryComment

        natureService = NatureServiceImpl(InMemoryNature)
        featureService = FeatureServiceImpl(InMemoryFeature, natureService)

        fieldBuilder = FieldBuilderImpl(MessageSimulator)
        entityBuilder =
                EntityBuilderImpl(MessageSimulator, fieldBuilder, featureService, InMemoryEmployee, InMemoryTask)

        rteConv = RowToEntityConverterImpl(MessageSimulator, entityBuilder, fieldBuilder, InMemoryTask, InMemoryComment)
    }
}