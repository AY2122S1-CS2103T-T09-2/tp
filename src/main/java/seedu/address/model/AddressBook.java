package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.model.modulelesson.ModuleLesson;
import seedu.address.model.modulelesson.UniqueModuleClassList;
import seedu.address.model.person.Person;
import seedu.address.model.person.UniquePersonList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueModuleClassList moduleClasses;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        moduleClasses = new UniqueModuleClassList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the class list with {@code classes}.
     * {@code classes} must not contain duplicate class;
     */
    public void setModuleClasses(List<ModuleLesson> classes) {
        moduleClasses.setModuleClasses(classes);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setModuleClasses(newData.getModuleClassList());
    }

    /**
     * Sorts the contacts in the address book by alphabetical order.
     */
    public void sortAddressBook() {
        persons.sortList();
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Returns true if a class with the same identity as {@code ModuleLesson} exists in the address book.
     */
    public boolean hasClass(ModuleLesson moduleLesson) {
        requireNonNull(moduleLesson);
        return moduleClasses.contains(moduleLesson);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Adds a class to the address book.
     * The class must not already exist in the address book.
     */
    public void addClass(ModuleLesson m) {
        moduleClasses.add(m);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Replaces the given class {@code target} in the list with {@code editedClass}.
     * {@code target} must exist in the address book.
     * The class information of {@code editedClass} must not be the same as another existing class in the address book.
     */
    public void setModuleClass(ModuleLesson target, ModuleLesson editedClass) {
        requireNonNull(editedClass);

        moduleClasses.setModuleClass(target, editedClass);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClass(ModuleLesson key) {
        moduleClasses.remove(key);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asUnmodifiableObservableList().size() + " persons";
        // TODO: refine later
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<ModuleLesson> getModuleClassList() {
        return moduleClasses.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && persons.equals(((AddressBook) other).persons))
                && moduleClasses.equals(((AddressBook) other).moduleClasses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persons, moduleClasses);
    }
}
