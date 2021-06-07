package org.edudev.arch.exceptions

class EntityNotFoundException(id: String) : RuntimeException("A entitidade com $id não existe!")