package qinglian.zeng.practice.handoversearchTechTest;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import qinglian.zeng.practice.handoversearchTechTest.TestResult.GRADE;

/**
 * @author quinglian.zeng
 *
 */
public class ReportGenerator
{

    /**
     * @param resultSheet
     * @return
     */
    public Map<String, TestResult.GRADE> getAverageGrade( Map<Student, Set<TestResult>> resultSheet ) {
        Map<String, TestResult.GRADE> result = new HashMap<>();
        if( resultSheet == null ) {
            return result;
        }
        resultSheet.forEach( ( k, v ) -> {
            result.put( k.getName(), getAverageGradeForEachStudent( v ) );
        } );

        return result;
    }

    public Map<Integer, TestResult.GRADE> getAgeToGradeCorrelation( Map<Student, Set<TestResult>> resultSheet ) {
        Map<Integer, TestResult.GRADE> result = new HashMap<>();
        Map<Integer, Map<TestResult.GRADE, Integer>> count = new HashMap<>();
        resultSheet.forEach( ( k, v ) -> {
            int age = k.getAge();
            Map<TestResult.GRADE, Integer> resultMap = count.get( age );
            if( resultMap == null ) {
                resultMap = new HashMap<>();
            }
            for( TestResult res : v ) {
                if( resultMap.containsKey( res.getGrade() ) ) {
                    resultMap.put( res.getGrade(), resultMap.get( res.getGrade() ) + 1 );
                } else {
                    resultMap.put( res.getGrade(), 1 );
                }
            }
            count.put( age, resultMap );

        } );

        count.forEach( ( k, v ) -> { // if two grade has the same frequent, choose better result
            int max = 0;
            TestResult.GRADE grade = null;
            for( Entry<GRADE, Integer> entry : v.entrySet() ) {
                if( entry.getValue() > max ) {
                    max = entry.getValue();
                    grade = entry.getKey();
                } else if( entry.getValue() == max && grade.getValue() > entry.getKey().getValue() ) {
                    max = entry.getValue();
                    grade = entry.getKey();
                }
            }
            result.put( k, grade );
        } );
        return result;
    }

    protected TestResult.GRADE getAverageGradeForEachStudent( Set<TestResult> results ) {
        // missing test, no result, then you get grade F.
        if( results.isEmpty() ) {
            return TestResult.GRADE.F;
        }
        float total = 0f;
        for( TestResult result : results ) {
            total += result.getGrade().getValue();
        }
        int avg = Math.round( total / results.size() );
        return getTestGradeFromScore( avg );
    }

    private TestResult.GRADE getTestGradeFromScore( int score ) {
        for( GRADE grade : GRADE.values() ) {
            if( score == grade.getValue() ) {
                return grade;
            }
        }
        return null;
    }
}
