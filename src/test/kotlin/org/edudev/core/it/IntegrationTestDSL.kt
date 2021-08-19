package org.edudev.core.it

import Main.logger
import io.restassured.http.ContentType.JSON
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.edudev.arch.domain.DomainEntity
import org.edudev.arch.repositories.Repository
import org.edudev.core.security.DefaultAuth.defaultAuthenticationHeader
import org.hamcrest.CoreMatchers.`is`
import org.junit.jupiter.api.Assertions.assertEquals

/**
 * @author Eduardo J
 * @description DSL que encapsula operações de testes do restassured em agregados.
 * @version 1.0
 */

/* Helper Functions */
fun <T : DomainEntity> Repository<T>.withData(data: Collection<T>, action: () -> Unit) {
    try {
        data.forEach(this::insert)
        action()
    } finally {
        data.forEach(this::remove)
    }
}

fun <T : DomainEntity> Repository<T>.withData(data: T, action: () -> Unit) {
    try {
        insert(data)
        action()
    } finally {
        remove(data)
    }
}

fun <T : DomainEntity> Repository<T>.cleanAfterFinish(data: T, action: () -> Unit) {
    try {
        action()
    } finally {
        remove(data)
    }
}

/* Helper Functions */

/* Change Operations */
fun <T : DomainEntity, DTO : Any> withInsert(
    entity: T,
    dto: DTO,
    repository: Repository<T>,
    assertFunction: (dto: DTO) -> Unit
) {
    repository.cleanAfterFinish(entity) {
        Given {
            contentType(JSON)
            defaultAuthenticationHeader()
        } When {
            body(dto)
            post()
        } Then {
            statusCode(201)
            val returnedDTO = extract().`as`(dto::class.java)
            assertFunction(returnedDTO)
            logger.info { " * * Inserção - [OK]  * *" }
        }
    }
}

inline fun <T : DomainEntity, DTO : Any> withUpdate(
    entity: T,
    dto: DTO,
    repository: Repository<T>,
    crossinline assertFunction: (dto: DTO) -> Unit
) {
    repository.withData(entity) {
        Given {
            contentType(JSON)
            defaultAuthenticationHeader()
        } When {
            body(dto)
            put(entity.id)
        } Then {
            statusCode(200)
            val returnedDTO = extract().`as`(dto::class.java)
            assertFunction(returnedDTO)
            logger.info { " * * Atualização - [OK]  * *" }
        }
    }
}

fun <T : DomainEntity> withDelete(
    entity: T,
    repository: Repository<T>
) {
    repository.withData(entity) {
        Given {
            contentType(JSON)
            defaultAuthenticationHeader()
        } When {
            delete(entity.id)
        } Then {
            statusCode(204)
            logger.info { " * * Deleção - [OK]  * *" }
        }
    }
}
/* Change Operations */


/* ReadOnly Operations */
fun <T : DomainEntity> withSize(
    repository: Repository<T>,
    entities: Collection<T>? = null
) {
    Given {
        contentType(JSON)
        defaultAuthenticationHeader()
    } When {
        get("/size")
    } Then {
        statusCode(200)
        body(`is`(repository.size().toString()))
        logger.info { "* * Contagem - [OK] * *" }
    }

    if (entities.isNullOrEmpty()) {
        logger.warn { "<!> Contagem com entidades - [IGNORADO] <!>" }
    } else {
        withSizeByProvidedData(repository, entities)
    }
}

fun <T : DomainEntity> withSizeByProvidedData(
    repository: Repository<T>,
    entities: Collection<T>
) {
    repository.withData(entities) {
        Given {
            contentType(JSON)
            defaultAuthenticationHeader()
        } When {
            get("/size")
        } Then {
            statusCode(200)
            val dtos = repository.list().filter { it.id != "adminId" }
            assertEquals(entities.size, dtos.size)
            logger.info { "* * Contagem com entidades - [OK] * *" }
        }
    }
}


inline fun <T : DomainEntity, reified DTO : Any, reified DTO_S : Any> withFindById(
    entity: T,
    repository: Repository<T>,
    crossinline assertFunction: (dto: DTO) -> Unit,
    noinline assertSummaryFunction: ((dto: DTO_S) -> Unit)? = null
) {
    repository.withData(entity) {
        Given {
            contentType(JSON)
            defaultAuthenticationHeader()
        } When {
            get("${entity.id}?summary=false")
        } Then {
            statusCode(200)
            val returnedDTO = extract().`as`(DTO::class.java)
            assertFunction(returnedDTO)
            logger.info { "* * Busca por ID - [OK] * *" }
        }
    }

    if (assertSummaryFunction == null) {
        logger.warn { "<!> Busca por ID Summarizado - [IGNORADO] <!>" }
    } else {
        withFindByIdSummarized(
            entity = entity,
            repository = repository,
            assertSummaryFunction = assertSummaryFunction
        )
    }
}

inline fun <T : DomainEntity, reified DTO_S : Any> withFindByIdSummarized(
    entity: T,
    repository: Repository<T>,
    crossinline assertSummaryFunction: (dto: DTO_S) -> Unit
) {
    repository.withData(entity) {
        Given {
            contentType(JSON)
            defaultAuthenticationHeader()
        } When {
            get("${entity.id}?summary=true")
        } Then {
            statusCode(200)
            val returnedDTO = extract().`as`(DTO_S::class.java)
            assertSummaryFunction(returnedDTO)
            logger.info { "* * Busca por ID Summarizado - [OK] * *" }
        }
    }
}

inline fun <T : DomainEntity, reified DTO : Any, reified DTO_S : Any> withList(
    entities: Collection<T>,
    repository: Repository<T>,
    crossinline assertFunction: (dto: Collection<DTO>) -> Unit,
    noinline assertSummaryFunction: ((dto: Collection<DTO_S>) -> Unit)? = null
) {
    repository.withData(entities) {
        Given {
            contentType(JSON)
            defaultAuthenticationHeader()
        } When {
            get("?summary=false")
        } Then {
            statusCode(200)
            val returnedDTOs = extract().jsonPath().getList(".", DTO::class.java)
            assertFunction(returnedDTOs)
            logger.info { " * * Listagem - [OK] * *" }
        }
    }

    if (assertSummaryFunction == null) {
        logger.warn {" <!> Listagem Summarizado - [IGNORADO] <!>" }
    } else {
        withListSummarized(
            entities = entities,
            repository = repository,
            assertSummaryFunction = assertSummaryFunction
        )
    }
}

inline fun <T : DomainEntity, reified DTO_S : Any> withListSummarized(
    entities: Collection<T>,
    repository: Repository<T>,
    crossinline assertSummaryFunction: (dto: Collection<DTO_S>) -> Unit
) {

    repository.withData(entities) {
        Given {
            contentType(JSON)
            defaultAuthenticationHeader()
        } When {
            get("?summary=true")
        } Then {
            statusCode(200)
            val returnedDTOs = extract().jsonPath().getList(".", DTO_S::class.java)
            assertSummaryFunction(returnedDTOs)
            logger.info { " * * Listagem Summarizado - [OK]  * *" }
        }
    }
}
/* ReadOnly Operations */


/* Operations Group */
inline fun <T: DomainEntity,  reified DTO: Any> withChangeOperations(
    entity: T,
    dto: DTO,
    newDataDTO: DTO? = null,
    repository: Repository<T>,
    noinline assertFunction: (dto: DTO) -> Unit,
    noinline assertUpdateFunction: ((dto: DTO) -> Unit)? = null,
    withInsert: Boolean = true,
    withUpdate: Boolean = true,
    withDelete: Boolean = true
) {

    if(withInsert) withInsert(entity, dto, repository, assertFunction)
    if(withUpdate && newDataDTO != null && assertUpdateFunction != null) withUpdate(entity, newDataDTO, repository, assertUpdateFunction)
    if(withDelete) withDelete(entity, repository)

    logger.info { "<< Fim das Operações ChangeOnly >>" }
}

inline fun <T : DomainEntity, reified DTO : Any, reified DTO_S : Any> withReadOperations(
    entity: T,
    entities: Collection<T>? = null,
    repository: Repository<T>,
    crossinline assertFunction: (dto: DTO) -> Unit,
    noinline assertListFunction: ((dto: Collection<DTO>) -> Unit)? = null,
    noinline assertSummaryFunction: ((dto: DTO_S) -> Unit)? = null,
    noinline assertListSummaryFunction: ((dto: Collection<DTO_S>) -> Unit)? = null,
    withSize: Boolean = true,
    withFindById: Boolean = true,
    withList: Boolean = true,
) {

    if (withSize) withSize(repository, entities)
    if (withFindById) withFindById(entity, repository, assertFunction, assertSummaryFunction)
    if (withList && !entities.isNullOrEmpty() && assertListFunction != null) withList(entities, repository, assertListFunction, assertListSummaryFunction)

    logger.info { "<< Fim das Operações ReadOnly >>" }
}

inline fun <T: DomainEntity, reified DTO: Any, reified DTO_S : Any> withCrudOperations(
    entity: T,
    entities: Collection<T>? = null,
    dto: DTO,
    newDataDTO: DTO? = null,
    repository: Repository<T>,
    noinline assertFunction: (dto: DTO) -> Unit,
    noinline assertSummaryFunction: ((dto: DTO_S) -> Unit)? = null,
    noinline assertUpdateFunction: ((dto: DTO) -> Unit)? = null,
    noinline assertListFunction: ((dto: Collection<DTO>) -> Unit)? = null,
    noinline assertListSummaryFunction: ((dto: Collection<DTO_S>) -> Unit)? = null,
    withInsert: Boolean = true,
    withUpdate: Boolean = true,
    withDelete: Boolean = true,
    withSize: Boolean = true,
    withFindById: Boolean = true,
    withList: Boolean = true
) {

    if(withInsert) withInsert(entity, dto, repository, assertFunction)
    if(withUpdate && newDataDTO != null && assertUpdateFunction != null) withUpdate(entity, newDataDTO, repository, assertUpdateFunction)
    if(withDelete) withDelete(entity, repository)
    if(withSize) withSize(repository, entities)
    if(withFindById) withFindById(entity, repository, assertFunction, assertSummaryFunction)
    if(withList && !entities.isNullOrEmpty() && assertListFunction != null) withList(entities, repository, assertListFunction, assertListSummaryFunction)

    logger.info { "<< Fim das Operações de Crud >>" }
}
/* Operations Group */
