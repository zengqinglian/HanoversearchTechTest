package qinglian.zeng.practice.handoversearchTechTest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class Student
{
    private final String name; // assume no student has the same name and it can be the identifier of student. Skip to create
                               // student ID in this case.
    private final int age; // In real world this should be birthday since age is changed every year.

    public Student( String name, int age ) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public int hashCode() {
        HashCodeBuilder hb = new HashCodeBuilder();
        hb.append( name );
        return hb.hashCode();
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj )
            return true;
        if( obj == null )
            return false;
        if( getClass() != obj.getClass() )
            return false;

        Student other = (Student) obj;

        EqualsBuilder eb = new EqualsBuilder();
        eb.append( other.getName(), this.name );
        return eb.isEquals();
    }

}
