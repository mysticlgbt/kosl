package io.kosl.build

import io.kosl.context.KoslContext
import io.kosl.execution.CommandName
import io.kosl.execution.RawArgument
import io.kosl.execution.RelativePath
import io.kosl.execution.SubCommandName

class BuildahEngine: BuildEngine {
  override fun process(context: KoslContext, job: BuildEngineJob) {
    val buildCommand = mutableListOf(
      CommandName("buildah"),
      SubCommandName("bud"),
      RawArgument("-t"),
      RawArgument("${job.targetImageName}:${job.targetImageTag}"),
      RawArgument("-f"),
      RelativePath(job.buildFilePath),
      RelativePath(job.contextDirectoryPath)
    )

    context.executeInteractiveProcess(buildCommand)

    if (job.push) {
      val pushCommand = mutableListOf(
        CommandName("buildah"),
        SubCommandName("push"),
        RawArgument("${job.targetImageName}:${job.targetImageTag}")
      )

      context.executeInteractiveProcess(pushCommand)
    }
  }
}
