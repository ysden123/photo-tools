/*
 * Copyright (c) 2022. StulSoft
 */

package com.stulsoft.photo.tools.emptydir;

import java.util.List;
import java.util.Optional;

public record EmptyDirResult(Optional<List<String>> result, Optional<String> error) {
}
