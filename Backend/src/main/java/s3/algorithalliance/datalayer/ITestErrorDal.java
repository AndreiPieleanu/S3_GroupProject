package s3.algorithalliance.datalayer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import s3.algorithalliance.datalayer.entities.TestErrorEntity;

import javax.persistence.NamedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface ITestErrorDal extends JpaRepository<TestErrorEntity, Integer> {

}
