package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Conthacks;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.modulelesson.ModuleLesson;
import seedu.address.model.person.Person;

/**
 * An Immutable Conthacks that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {

    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_LESSON = "Lesson list contains duplicate lesson(es)";

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedModuleLesson> lessons = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given persons and lessons.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
                                       @JsonProperty("lessons") List<JsonAdaptedModuleLesson> lessons) {
        this.persons.addAll(persons);
        this.lessons.addAll(lessons);
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        lessons.addAll(source.getModuleLessonList().stream().map(JsonAdaptedModuleLesson::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code Conthacks} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public Conthacks toModelType() throws IllegalValueException {
        Conthacks conthacks = new Conthacks();
        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (conthacks.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            conthacks.addPerson(person);
        }

        for (JsonAdaptedModuleLesson jsonAdaptedModuleLesson : lessons) {
            ModuleLesson moduleLesson = jsonAdaptedModuleLesson.toModelType();
            if (conthacks.hasLesson(moduleLesson)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_LESSON);
            }
            conthacks.addLesson(moduleLesson);
        }
        return conthacks;
    }

}
