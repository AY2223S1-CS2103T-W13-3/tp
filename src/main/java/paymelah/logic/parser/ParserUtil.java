package paymelah.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import paymelah.commons.core.index.Index;
import paymelah.commons.util.CollectionUtil;
import paymelah.commons.util.StringUtil;
import paymelah.logic.parser.exceptions.ParseException;
import paymelah.model.debt.DebtDate;
import paymelah.model.debt.DebtTime;
import paymelah.model.debt.Description;
import paymelah.model.debt.Money;
import paymelah.model.person.Address;
import paymelah.model.person.DebtContainsKeywordsPredicate;
import paymelah.model.person.DebtGreaterEqualAmountPredicate;
import paymelah.model.person.Name;
import paymelah.model.person.Phone;
import paymelah.model.person.Telegram;
import paymelah.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser
 * classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading
     * and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero
     *                        unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code Collection<String> oneBasedIndexes} into a {@code Set<Index>} and returns it. Leading and
     * trailing whitespaces will be trimmed.
     *
     * @param indices String of valid indices (non-zero unsigned integer).
     * @return {@code Set<Index>} of indices parsed from given String.
     * @throws ParseException if any index is invalid (not non-zero unsigned integer).
     */
    public static Set<Index> parseIndices(String indices) throws ParseException {
        requireNonNull(indices);
        String trimmedIndices = indices.trim();
        List<String> oneBasedIndices = Arrays.stream(trimmedIndices.replaceAll("\\s+", " ")
                .split(" ")).collect(Collectors.toList());

        final Set<Index> indexSet = new HashSet<>();
        for (String index : oneBasedIndices) {
            indexSet.add(parseIndex(index));
        }

        return indexSet;
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String telegram} into an {@code Telegram}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code telegram} is invalid.
     */
    public static Telegram parseTelegram(String telegram) throws ParseException {
        requireNonNull(telegram);
        String trimmedTelegram = telegram.trim();
        if (!Telegram.isValidHandle(trimmedTelegram)) {
            throw new ParseException(Telegram.MESSAGE_CONSTRAINTS);
        }
        return new Telegram(trimmedTelegram);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String description} into a {@code Description}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param description The description to turn into a {@code Description} object.
     * @return The corresponding {@code Description}.
     * @throws ParseException if the given {@code description} is invalid.
     */
    public static Description parseDescription(String description) throws ParseException {
        requireNonNull(description);
        String trimmedDescription = description.trim();
        if (!Description.isValidDescription(trimmedDescription)) {
            throw new ParseException(Description.MESSAGE_CONSTRAINTS);
        }
        return new Description(trimmedDescription);
    }

    /**
     * Parses {@code Collection<String> descriptions} into a {@code Set<Description>}.
     * @param descriptions the Collection of descriptions to parse
     * @return a Set of Descriptions
     * @throws ParseException if a description cannot be parsed
     */
    public static Set<Description> parseDescriptions(Collection<String> descriptions) throws ParseException {
        requireNonNull(descriptions);
        final Set<Description> descriptionSet = new HashSet<>();
        for (String description : descriptions) {
            descriptionSet.add(parseDescription(description));
        }
        return descriptionSet;
    }

    /**
     * Parses a {@code String money} into a {@code Money}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param money The money to turn into a {@code Money} object.
     * @return The corresponding {@code Money}.
     * @throws ParseException if the given {@code money} is invalid.
     */
    public static Money parseMoney(String money) throws ParseException {
        requireNonNull(money);
        String trimmedMoney = money.trim();
        if (!Money.isValidMoney(trimmedMoney)) {
            throw new ParseException(Money.MESSAGE_CONSTRAINTS);
        }
        return new Money(trimmedMoney);
    }

    /**
     * Parses {@code Collection<String> monies} into a {@code Set<Money>}.
     * @param monies the Collection of monies to parse
     * @return a Set of Moneys
     * @throws ParseException if a money cannot be parsed
     */
    public static Set<Money> parseMonies(Collection<String> monies) throws ParseException {
        requireNonNull(monies);
        final Set<Money> moneySet = new HashSet<>();
        for (String money : monies) {
            moneySet.add(parseMoney(money));
        }
        return moneySet;
    }

    /**
     * Parses a {@code String date} into a {@code DebtDate}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param date The date to turn into a {@code DebtDate} object.
     * @return The corresponding {@code DebtDate}.
     * @throws ParseException if the given {@code date} is invalid.
     */
    public static DebtDate parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        if (!DebtDate.isValidDate(trimmedDate)) {
            throw new ParseException(DebtDate.MESSAGE_CONSTRAINTS);
        }
        return new DebtDate(trimmedDate);
    }

    /**
     * Parses {@code Collection<String> dates} into a {@code Set<DebtDate>}.
     * @param dates the Collection of dates to parse
     * @return a Set of DebtDates
     * @throws ParseException if a date cannot be parsed
     */
    public static Set<DebtDate> parseDates(Collection<String> dates) throws ParseException {
        requireNonNull(dates);
        final Set<DebtDate> dateSet = new HashSet<>();
        for (String date : dates) {
            dateSet.add(parseDate(date));
        }
        return dateSet;
    }

    /**
     * Parses a {@code String time} into a {@code DebtTime}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param time The time to turn into a {@code DebtTime} object.
     * @return The corresponding {@code DebtTime}.
     * @throws ParseException if the given {@code time} is invalid.
     */
    public static DebtTime parseTime(String time) throws ParseException {
        requireNonNull(time);
        String trimmedTime = time.trim();
        if (!DebtTime.isValidTime(trimmedTime)) {
            throw new ParseException(DebtTime.MESSAGE_CONSTRAINTS);
        }
        return new DebtTime(trimmedTime);
    }

    /**
     * Parses {@code Collection<String> times} into a {@code Set<DebtTime>}.
     * @param times the Collection of times to parse
     * @return a Set of DebtTimes
     * @throws ParseException if a time cannot be parsed
     */
    public static Set<DebtTime> parseTimes(Collection<String> times) throws ParseException {
        requireNonNull(times);
        final Set<DebtTime> timeSet = new HashSet<>();
        for (String time : times) {
            timeSet.add(parseTime(time));
        }
        return timeSet;
    }

    /**
     * Parses {@code String s} into a {@code DebtContainsKeywordsPredicate}.
     */
    public static DebtContainsKeywordsPredicate prepareDebtContainsKeywordsPredicate(String s) throws ParseException {
        requireNonNull(s);
        String trimmed = s.trim();
        if (trimmed.isEmpty()) {
            throw new ParseException(DebtContainsKeywordsPredicate.MESSAGE_CONSTRAINTS);
        }
        return new DebtContainsKeywordsPredicate(Arrays.asList(trimmed.split("\\s+")));
    }

    /**
     * Parses {@code String s} into a {@code DebtGreaterEqualAmountPredicate}.
     */
    public static DebtGreaterEqualAmountPredicate prepareDebtGreaterEqualAmountPredicate(String s)
            throws ParseException {
        Money m = parseMoney(s);
        return new DebtGreaterEqualAmountPredicate(m);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if exactly one of the prefixes contains non-empty {@code Optional} values
     * in the given
     * {@code ArgumentMultimap}.
     */
    public static boolean isExactlyOneOfPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        int presentCount = 0;
        for (Prefix prefix : prefixes) {
            if (argumentMultimap.getValue(prefix).isPresent()) {
                presentCount++;
            }
        }
        return presentCount == 1;
    }

    /**
     * Stores a descriptor of a {@code Person}; all fields are optional.
     */
    public static class PersonDescriptor {
        private Name name;
        private Phone phone;
        private Telegram telegram;
        private Address address;
        private Set<Tag> tags;
        private Set<Description> descriptions;
        private Set<Money> monies;
        private Set<DebtDate> dates;
        private Set<DebtTime> times;

        public PersonDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public PersonDescriptor(PersonDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setTelegram(toCopy.telegram);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setDescriptions(toCopy.descriptions);
            setMonies(toCopy.monies);
            setDates(toCopy.dates);
            setTimes(toCopy.times);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldSet() {
            return CollectionUtil.isAnyNonNull(name, phone, telegram, address,
                                                tags, descriptions, monies, dates, times);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setTelegram(Telegram telegram) {
            this.telegram = telegram;
        }

        public Optional<Telegram> getTelegram() {
            return Optional.ofNullable(telegram);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code descriptions} to this object's {@code descriptions}.
         * A defensive copy of {@code descriptions} is used internally.
         */
        public void setDescriptions(Set<Description> descriptions) {
            this.descriptions = (descriptions != null) ? new HashSet<>(descriptions) : null;
        }

        /**
         * Returns an unmodifiable description set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code descriptions} is null.
         */
        public Optional<Set<Description>> getDescriptions() {
            return (descriptions != null) ? Optional.of(Collections.unmodifiableSet(descriptions)) : Optional.empty();
        }

        /**
         * Sets {@code monies} to this object's {@code monies}.
         * A defensive copy of {@code monies} is used internally.
         */
        public void setMonies(Set<Money> monies) {
            this.monies = (monies != null) ? new HashSet<>(monies) : null;
        }

        /**
         * Returns an unmodifiable money set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code monies} is null.
         */
        public Optional<Set<Money>> getMonies() {
            return (monies != null) ? Optional.of(Collections.unmodifiableSet(monies)) : Optional.empty();
        }

        /**
         * Sets {@code dates} to this object's {@code dates}.
         * A defensive copy of {@code dates} is used internally.
         */
        public void setDates(Set<DebtDate> dates) {
            this.dates = (dates != null) ? new HashSet<>(dates) : null;
        }

        /**
         * Returns an unmodifiable date set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code dates} is null.
         */
        public Optional<Set<DebtDate>> getDates() {
            return (dates != null) ? Optional.of(Collections.unmodifiableSet(dates)) : Optional.empty();
        }

        /**
         * Sets {@code times} to this object's {@code times}.
         * A defensive copy of {@code times} is used internally.
         */
        public void setTimes(Set<DebtTime> times) {
            this.times = (times != null) ? new HashSet<>(times) : null;
        }

        /**
         * Returns an unmodifiable time set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code times} is null.
         */
        public Optional<Set<DebtTime>> getTimes() {
            return (times != null) ? Optional.of(Collections.unmodifiableSet(times)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof PersonDescriptor)) {
                return false;
            }

            // state check
            PersonDescriptor pd = (PersonDescriptor) other;

            return getName().equals(pd.getName())
                    && getPhone().equals(pd.getPhone())
                    && getTelegram().equals(pd.getTelegram())
                    && getAddress().equals(pd.getAddress())
                    && getTags().equals(pd.getTags())
                    && getDescriptions().equals(pd.getDescriptions())
                    && getMonies().equals(pd.getMonies())
                    && getDates().equals(pd.getDates())
                    && getTimes().equals(pd.getTimes());
        }
    }
}
