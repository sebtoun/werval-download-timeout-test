package controllers;

import io.werval.api.outcomes.Outcome;
import utils.RandomInputStream;

import static io.werval.api.context.CurrentContext.*;
import static io.werval.api.mime.MimeTypesNames.APPLICATION_OCTET_STREAM;
import static io.werval.modules.json.JSON.*;

public class Application
{
    public final int LONG_FILE_SIZE = 1024 * 1024 * 64; // 64 Mo
    public Outcome index()
    {
        // Welcome Page
        // return new io.werval.controllers.Welcome().welcome();

        // It works!
        // return outcomes().ok( "It works!" ).build();
        
        // JSON Object
        // return outcomes().ok( json().toJSON( json().newObject().put( "message", "It works!" ) ) ).asJson().build();

        return outcomes().ok().withBody(new RandomInputStream(LONG_FILE_SIZE)).as( APPLICATION_OCTET_STREAM ).build();
    }
}
