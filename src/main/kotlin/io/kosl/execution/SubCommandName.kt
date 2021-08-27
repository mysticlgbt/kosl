package io.kosl.execution

import java.nio.file.Path

class SubCommandName(val subcommand: String): ExecutionParameter() {
  override fun toCommandArgument(localContextDirectory: Path): String = subcommand
}
