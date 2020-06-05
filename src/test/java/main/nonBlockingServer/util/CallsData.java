package main.nonBlockingServer.util;

import main.entity.Calls;
import main.entity.CallsNew;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

public class CallsData {
    public static final Calls calls = new Calls(new ObjectId (),
    7,
    8,
    3,
    2020,
    1200,
    "55",
    "55",
    "55",
    "55",
    "55",
    "55",
    "55",
    "55",
    "55"
    );

    public static final CallsNew callsNew = new CallsNew(new ObjectId (),
            7,
            LocalDateTime.of(2020, 3,8, 12,00),
            "55",
            "55",
            "55",
            "55",
            "55",
            "55",
            "55",
            "55",
            "55"
    );
}
