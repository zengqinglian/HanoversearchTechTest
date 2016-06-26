package qinglian.zeng.practice.handoversearchTechTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class ReportGeneratorTest
{

    private ReportGenerator reporter;
    private Set<TestResult> resultsA;
    private Set<TestResult> resultsB;
    private Set<TestResult> resultsC;
    private Set<TestResult> resultsD;
    private Student studentA;
    private Student studentB;
    private Student studentC;
    private Student studentD;

    private static final String SUBJECT_MATH = "Maths";
    private static final String SUBJECT_ENG = "English";
    private static final String SUBJECT_SCI = "Science";

    private static final String A_NAME = "Oliver";
    private static final String B_NAME = "Jack";
    private static final String C_NAME = "Charlie";
    private static final String D_NAME = "Harry";

    private static final int A_AGE = 6;
    private static final int B_AGE = 8;
    private static final int C_AGE = 8;
    private static final int D_AGE = 9;

    private static final String testFile = "testRecord.txt";

    @Before
    public void setUp() {
        reporter = new ReportGenerator();
        resultsD = new HashSet<>();
        resultsA = new HashSet<>();
        resultsB = new HashSet<>();
        resultsC = new HashSet<>();
        studentA = new Student( A_NAME, A_AGE );
        studentB = new Student( B_NAME, B_AGE );
        studentC = new Student( C_NAME, C_AGE );
        studentD = new Student( D_NAME, D_AGE );

        resultsA.add( new TestResult( TestResult.GRADE.A, SUBJECT_MATH ) );
        resultsA.add( new TestResult( TestResult.GRADE.B, SUBJECT_ENG ) );
        resultsA.add( new TestResult( TestResult.GRADE.B, SUBJECT_SCI ) );

        resultsB.add( new TestResult( TestResult.GRADE.A, SUBJECT_MATH ) );
        resultsB.add( new TestResult( TestResult.GRADE.A, SUBJECT_ENG ) );
        resultsB.add( new TestResult( TestResult.GRADE.C, SUBJECT_SCI ) );

        resultsC.add( new TestResult( TestResult.GRADE.D, SUBJECT_MATH ) );
        resultsC.add( new TestResult( TestResult.GRADE.D, SUBJECT_ENG ) );
        resultsC.add( new TestResult( TestResult.GRADE.D, SUBJECT_SCI ) );

        resultsD.add( new TestResult( TestResult.GRADE.E, SUBJECT_MATH ) );
        resultsD.add( new TestResult( TestResult.GRADE.D, SUBJECT_ENG ) );
        resultsD.add( new TestResult( TestResult.GRADE.E, SUBJECT_SCI ) );

    }

    @Test
    public void calcualteAvergageGradeForEachStudent() {
        assertEquals( TestResult.GRADE.F, reporter.getAverageGradeForEachStudent( new HashSet<>() ) );

        assertEquals( TestResult.GRADE.B, reporter.getAverageGradeForEachStudent( resultsA ) );

        assertEquals( TestResult.GRADE.B, reporter.getAverageGradeForEachStudent( resultsB ) );

        assertEquals( TestResult.GRADE.D, reporter.getAverageGradeForEachStudent( resultsC ) );

        assertEquals( TestResult.GRADE.E, reporter.getAverageGradeForEachStudent( resultsD ) );

    }

    @Test
    public void calcualteAvergageGrade() {
        Map<Student, Set<TestResult>> resultSheet = new HashMap<>();
        // test null
        assertTrue( reporter.getAverageGrade( resultSheet ).isEmpty() );
        // test empty resultSheet
        assertTrue( reporter.getAverageGrade( resultSheet ).isEmpty() );

        resultsA.add( new TestResult( TestResult.GRADE.A, SUBJECT_MATH ) );
        resultsA.add( new TestResult( TestResult.GRADE.B, SUBJECT_ENG ) );
        resultsA.add( new TestResult( TestResult.GRADE.B, SUBJECT_SCI ) );

        resultSheet.put( studentA, resultsA );
        resultSheet.put( studentB, resultsB );
        resultSheet.put( studentC, resultsC );
        resultSheet.put( studentD, resultsD );

        Map<String, TestResult.GRADE> result = reporter.getAverageGrade( resultSheet );
        assertEquals( 4, result.size() );
        assertEquals( TestResult.GRADE.B, result.get( A_NAME ) );
        assertEquals( TestResult.GRADE.B, result.get( B_NAME ) );
        assertEquals( TestResult.GRADE.D, result.get( C_NAME ) );
        assertEquals( TestResult.GRADE.E, result.get( D_NAME ) );

    }

    @Test
    public void getAgeToGradeCorrelationTest() throws IOException {
        // test null and test empty input
        Map<Integer, TestResult.GRADE> resultEmpty = reporter.getAgeToGradeCorrelation( new HashMap<Student, Set<TestResult>>() );
        assertTrue( resultEmpty.isEmpty() );
        resultEmpty = reporter.getAgeToGradeCorrelation( null );
        assertTrue( resultEmpty.isEmpty() );

        // test default Grade f
        Map<Student, Set<TestResult>> defaultResultSheet = new HashMap<>();
        defaultResultSheet.put( studentA, null );
        defaultResultSheet.put( studentB, null );
        defaultResultSheet.put( studentC, null );
        defaultResultSheet.put( studentD, null );
        Map<Integer, TestResult.GRADE> defaultResult = reporter.getAgeToGradeCorrelation( defaultResultSheet );
        assertEquals( TestResult.GRADE.F, defaultResult.get( 6 ) );
        assertEquals( TestResult.GRADE.F, defaultResult.get( 8 ) );
        assertEquals( TestResult.GRADE.F, defaultResult.get( 9 ) );

        defaultResultSheet.put( studentA, new HashSet<TestResult>() );
        defaultResultSheet.put( studentB, new HashSet<TestResult>() );
        defaultResultSheet.put( studentC, new HashSet<TestResult>() );
        defaultResultSheet.put( studentD, new HashSet<TestResult>() );
        defaultResult = reporter.getAgeToGradeCorrelation( defaultResultSheet );
        assertEquals( TestResult.GRADE.F, defaultResult.get( 6 ) );
        assertEquals( TestResult.GRADE.F, defaultResult.get( 8 ) );
        assertEquals( TestResult.GRADE.F, defaultResult.get( 9 ) );

        // test normal data
        Map<Student, Set<TestResult>> resultSheet = loadTestData();
        Map<Integer, TestResult.GRADE> result = reporter.getAgeToGradeCorrelation( resultSheet );
        assertEquals( TestResult.GRADE.B, result.get( 5 ) );
        assertEquals( TestResult.GRADE.A, result.get( 6 ) );
        assertEquals( TestResult.GRADE.A, result.get( 7 ) );// A and B both appeared 4 times, according to the rule, it should be A.
        assertEquals( TestResult.GRADE.A, result.get( 8 ) );
        assertEquals( TestResult.GRADE.E, result.get( 9 ) );
    }

    private Map<Student, Set<TestResult>> loadTestData() throws IOException {
        Map<Student, Set<TestResult>> resultSheet = new HashMap<>();
        ClassLoader loader = Test.class.getClassLoader();
        URL url = loader.getResource( testFile );
        assertNotNull( url );
        File file = new File( url.getFile() );

        try (BufferedReader br = new BufferedReader( new FileReader( file ) )) {

            String line = br.readLine();

            while( line != null ) {
                String[] record = line.split( "," );
                Student stu = new Student( record[0], Integer.valueOf( record[1] ) );
                Set<TestResult> result = new HashSet<>();
                TestResult math = new TestResult( getTestGrade( record[2] ), SUBJECT_MATH );
                TestResult eng = new TestResult( getTestGrade( record[3] ), SUBJECT_ENG );
                TestResult sci = new TestResult( getTestGrade( record[4] ), SUBJECT_SCI );
                result.add( math );
                result.add( eng );
                result.add( sci );
                resultSheet.put( stu, result );
                line = br.readLine();
            }
        }
        return resultSheet;
    }

    private TestResult.GRADE getTestGrade( String gradeString ) {
        for( TestResult.GRADE grade : TestResult.GRADE.values() ) {
            if( grade.toString().equals( gradeString ) ) {
                return grade;
            }
        }
        return TestResult.GRADE.F; // missing grade, it will be F.
    }
}
