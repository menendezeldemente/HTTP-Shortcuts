package ch.rmy.android.http_shortcuts.usecases

import ch.rmy.android.http_shortcuts.data.domains.working_directories.WorkingDirectoryId
import ch.rmy.android.http_shortcuts.data.domains.working_directories.WorkingDirectoryRepository
import ch.rmy.android.http_shortcuts.data.models.Base
import ch.rmy.android.http_shortcuts.data.models.WorkingDirectory
import javax.inject.Inject

class GetUsedWorkingDirectoryIdsUseCase
@Inject
constructor(
    private val workingDirectoryRepository: WorkingDirectoryRepository,
) {
    suspend operator fun invoke(base: Base): Set<WorkingDirectoryId> =
        buildSet {
            val workingDirectories = workingDirectoryRepository.getWorkingDirectories()
            base.shortcuts.forEach { shortcut ->
                shortcut.responseHandling?.storeDirectoryId
                    ?.let(::add)
                addAll(extractFromCode(shortcut.codeOnPrepare, workingDirectories))
                addAll(extractFromCode(shortcut.codeOnSuccess, workingDirectories))
                addAll(extractFromCode(shortcut.codeOnFailure, workingDirectories))
            }
            addAll(extractFromCode(base.globalCode.orEmpty(), workingDirectories))
        }

    private fun extractFromCode(code: String, workingDirectories: List<WorkingDirectory>): Sequence<WorkingDirectoryId> =
        GET_DIRECTORY_CALL_SITE_REGEX.findAll(code)
            .mapNotNull { matchResult ->
                workingDirectories
                    .findByNameOrId(nameOrId = matchResult.groupValues[1].drop(1).dropLast(1))
                    ?.id
            }

    private fun List<WorkingDirectory>.findByNameOrId(nameOrId: String): WorkingDirectory? =
        find { it.id == nameOrId || it.name.equals(nameOrId, ignoreCase = true) }

    companion object {
        private val GET_DIRECTORY_CALL_SITE_REGEX =
            """getDirectory\(("[^"]+"|'[^']+')\)""".toRegex()
    }
}
