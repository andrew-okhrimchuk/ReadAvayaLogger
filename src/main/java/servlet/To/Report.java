package servlet.To;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonBlockingEchoServer.util.ToCalls;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Report implements Serializable {
    private LocalDateTime start, end;
    private List<ToCalls>  request;
}