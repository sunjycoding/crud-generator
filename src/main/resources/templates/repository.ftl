package ${basePackage}.${moduleName}.repository;

import ${basePackage}.${moduleName}.entity.${tableNameFirstLetterUppercase}Entity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author created by ${author} on ${date}
 */
@Repository
public interface ${tableNameFirstLetterUppercase}Repository extends JpaRepository<${tableNameFirstLetterUppercase}Entity, String> {

}
