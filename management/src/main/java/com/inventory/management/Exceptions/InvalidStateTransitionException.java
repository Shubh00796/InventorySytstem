package com.inventory.management.Exceptions;

import com.inventory.management.Enums.ReportType;

public class InvalidStateTransitionException extends Throwable {
    public InvalidStateTransitionException(ReportType oldStatus, ReportType newStatus) {

    }
}
