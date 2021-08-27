package io.kosl.build

import io.kosl.context.KoslContext
import io.kosl.execution.CommandName
import io.kosl.execution.RawArgument
import io.kosl.execution.RelativePath

class DockerBuildxEngine: BuildEngine {
  override fun process(context: KoslContext, job: BuildEngineJob) {
    val command = mutableListOf(
      CommandName("docker"),
      RawArgument("buildx"),
      RawArgument("build"),
      RawArgument("-t"),
      RawArgument("${job.targetImageName}:${job.targetImageTag}"),
      RawArgument("-f"),
      RelativePath(job.buildFilePath)
    )

    if (job.push) {
      command.add(RawArgument("--push"))
    } else {
      command.add(RawArgument("--load"))
    }

    command.add(RelativePath(job.contextDirectoryPath))
    context.executeInteractiveProcess(command)
  }
}
