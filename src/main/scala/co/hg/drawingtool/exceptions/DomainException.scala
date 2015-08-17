package co.hg.drawingtool.exceptions


abstract class DomainException(reason: String)

class DrawException(reason: String) extends DomainException(reason)

class BuildException(reason: String) extends DomainException(reason)

class PreconditionException(reason: String) extends DomainException(reason)