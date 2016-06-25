package qinglian.zeng.practice.handoversearchTechTest;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class TestResult
{
    private final GRADE grade;
    private final String subject;

    public TestResult( GRADE grade, String subject ) {
        this.grade = grade;
        this.subject = subject;
    }

    // if one subject has no result or missing result, it will be set to Grade F.
    public TestResult( String subject ) {
        this( GRADE.F, subject );
    }

    public GRADE getGrade() {
        return grade;
    }

    public String getSubject() {
        return subject;
    }

    @Override
    public int hashCode() { // assume every student has one grade per subject
        HashCodeBuilder hb = new HashCodeBuilder();
        hb.append( subject );
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
        TestResult other = (TestResult) obj;

        EqualsBuilder eb = new EqualsBuilder();
        eb.append( other.getSubject(), this.subject );
        return eb.isEquals();
    }

    public static enum GRADE
    {
        A {
            @Override
            public int getValue() {

                return 1;
            }

        },
        B {

            @Override
            public int getValue() {
                return 2;
            }

        },
        C {

            @Override
            public int getValue() {

                return 3;
            }

        },
        D {

            @Override
            public int getValue() {

                return 4;
            }

        },
        E {

            @Override
            public int getValue() {

                return 5;
            }

        },
        F {

            @Override
            public int getValue() {

                return 6;
            }

        };

        public abstract int getValue();

    }
}
