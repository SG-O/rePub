package de.sg_o.lib.rePub.opfPack;

import org.jdom2.Attribute;
import org.jdom2.Element;
import org.jdom2.Namespace;

import java.util.Objects;

public class Contributor {
    private static final Namespace opfNamespace = Namespace.getNamespace("opf", "http://www.idpf.org/2007/opf");

    private String name;
    private Relator role = null;
    private String fileAs = null;

    //toDo AlternateScript


    public Contributor(String name) {
        this.name = name;
    }

    public void setRole(Element role, Element element) {
        if (role != null) {
            this.role = Relator.fromCode(role.getValue());
            return;
        }
        if (element != null) {
            Attribute opfRole = element.getAttribute("role", opfNamespace);
            if (opfRole != null) {
                this.role = Relator.fromCode(opfRole.getValue());
            }
        }
    }

    public void setFileAs(Element fileAs) {
        if (fileAs == null) {
            this.fileAs = null;
            return;
        }
        this.fileAs = fileAs.getValue();
    }

    public String getName() {
        return name;
    }

    public Relator getRole() {
        return role;
    }

    public String getFileAs() {
        return fileAs;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contributor that = (Contributor) o;
        return Objects.equals(name, that.name) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, role);
    }

    @Override
    public String toString() {
        return "Contributor{" +
                "name='" + name + '\'' +
                ", role=" + role +
                ", fileAs='" + fileAs + '\'' +
                '}';
    }
}
