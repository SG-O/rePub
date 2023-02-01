package de.sg_o.lib.rePub.opfPack;

import org.jdom2.Element;

import java.util.Objects;

public class Subject {
    private final String subject;
    private String authority;
    private String term;

    public Subject(String subject) {
        this.subject = subject;
    }

    public void setAuthority(Element authority) {
        if (authority == null) return;
        String value = authority.getValue();
        if (value == null) return;
        this.authority = value;
    }

    public void setTerm(Element term) {
        if (term == null) return;
        String value = term.getValue();
        if (value == null) return;
        this.term = value;
    }

    public String getSubject() {
        return subject;
    }

    public String getAuthority() {
        if (term == null) return null;
        return authority;
    }

    public String getTerm() {
        if (authority == null) return null;
        return term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subject subject1 = (Subject) o;
        return Objects.equals(subject, subject1.subject) && Objects.equals(authority, subject1.authority) && Objects.equals(term, subject1.term);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, authority, term);
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subject='" + subject + '\'' +
                ", authority='" + getAuthority() + '\'' +
                ", term='" + getTerm() + '\'' +
                '}';
    }
}
