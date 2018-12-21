package ru.tsystems.divider.service.impl.functional

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.tsystems.divider.dao.api.TaskDao
import ru.tsystems.divider.entity.Comment
import ru.tsystems.divider.entity.Employee
import ru.tsystems.divider.entity.Feature
import ru.tsystems.divider.entity.Task
import ru.tsystems.divider.service.api.functional.CommentBuilder
import ru.tsystems.divider.service.api.functional.EmployeeBuilder
import ru.tsystems.divider.service.api.functional.EntityBuilder
import ru.tsystems.divider.service.api.functional.FeatureBuilder
import ru.tsystems.divider.service.api.functional.FieldBuilder
import ru.tsystems.divider.utils.api.MessageWorker
import ru.tsystems.divider.utils.constants.PROPS_MODIFICATOR_KEYS_PRE
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_ANOTHER
import ru.tsystems.divider.utils.constants.PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS

@Service
class EntityBuilderImpl(
    @Autowired messageWorker: MessageWorker,
    @Autowired val fieldBuilder: FieldBuilder,
    @Autowired val featureBuilder: FeatureBuilder,
    @Autowired val employeeBuilder: EmployeeBuilder,
    @Autowired val taskDao: TaskDao,
    @Autowired val commentBuilder: CommentBuilder
) : EntityBuilder {

    companion object {
        //private val logger = //logger.getLogger(EntityBuilderImpl::class.java)
    }

    private val ANOTHER_TASKS_DIVIDER: String
    private val KEY_MODIFICATOR: String
    private val ANOTHER_DIVIDER: String

    init {
        KEY_MODIFICATOR = messageWorker.getObligatorySourceValue(PROPS_MODIFICATOR_KEYS_PRE)


        ANOTHER_TASKS_DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_ANOTHER_TASKS)
        ANOTHER_DIVIDER = messageWorker.getObligatorySourceValue(PROPS_SYMBOLS_SOURCE_ANOTHER)
    }

    /**
     * Make a Employee entity and return it
     *
     * @param employee String with employee.
     * @return New Employee entity.
     */
    override fun buildEmployee(employee: String): Employee? {
        return if (employee.isEmpty()) null else employeeBuilder.buildEmployee(employee)
    }

    /**
     * Make a team entity and return it
     *
     * @param team team.
     * @return New team entity.
     */
    override fun buildFeatureSet(title: String, nature: String): Set<Feature> {
        return if (title.isEmpty()) {
            HashSet<Feature>()
        } else {
            featureBuilder.buildFeatureSet(features = title, nature = nature)
        }


    }

    /**
     * Make a Comment entity and return it
     *
     * @param comment Comment.
     * @return New comment.
     */
    override fun buildComments(comment: String): Comment? {
        return if (comment.isEmpty()) {
            null
        } else {
            commentBuilder.buildComment(comment)
        }
    }

    /**
     * Try to find one param entity. If it doesn't exist, persist new.
     *
     * @param title  Title
     * @param nature Nature constant.
     * @return OneParamEntity
     */
    override fun buildFeature(title: String, nature: String): Feature? {
        return if (title.isEmpty()) {
            null
        } else {
            return featureBuilder.buildFeature(title, nature)
        }

    }


    /**
     * Make a subtask set and return it
     *
     * @param subTasks version.
     * @return Set with version entities.
     */
    override fun buildConnectionToAnotherTasks(subTasks: String): Set<Task> {
        if (subTasks.isEmpty())
            return HashSet()
        val tasks = HashSet<Task>()

        val keys = fieldBuilder.rebuildString(subTasks, ANOTHER_TASKS_DIVIDER)
        for (key in keys) {
            try {
                tasks.add(buildSubTask(fieldBuilder.buildTaskKey(key, KEY_MODIFICATOR)))
            } catch (ilStExc: IllegalStateException) {
                //logger.error(ilStExc.message + "; This task not added to dependencies!")
            }
        }
        return tasks

    }

    private fun buildSubTask(key: String): Task {
        return taskDao.getBykey(key) ?: throw IllegalStateException("Illegal task connection with key: $key")
    }
}