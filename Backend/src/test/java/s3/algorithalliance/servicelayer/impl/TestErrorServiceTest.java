package s3.algorithalliance.servicelayer.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import s3.algorithalliance.datalayer.ITestErrorDal;
import s3.algorithalliance.datalayer.entities.TestErrorEntity;
import s3.algorithalliance.domain.TestError;
import s3.algorithalliance.domain.reqresp.testerror.GetTestErrorsResponse;
import s3.algorithalliance.servicelayer.ITestErrorService;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TestErrorServiceTest {
    ITestErrorDal testErrorDal = mock(ITestErrorDal.class);
    ITestErrorService testErrorService = new TestErrorService(testErrorDal);
    
    @Test
    public void getTestErrorsShouldReturnErrors_WhenErrorsExist(){
        when(testErrorDal.findAll()).thenReturn(List.of(
                TestErrorEntity.builder().id(1).errorCode("1").build(),
                TestErrorEntity.builder().id(2).errorCode("2").build()
        ));
        Map<Integer, TestError> expectedErrors = Map.of(
                1,
                TestError.builder().id(1).errorCode("1").build(),
                2,
                TestError.builder().id(2).errorCode("2").build()
        );
        GetTestErrorsResponse response = testErrorService.getTestErrors();
        assertNotNull(response);
        assertEquals(expectedErrors, response.getTestErrors());
    }
}