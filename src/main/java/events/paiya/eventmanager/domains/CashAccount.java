package events.paiya.eventmanager.domains;

import events.paiya.eventmanager.enumeration.FinancialAccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashAccount {
    private String id;
    private FinancialAccountType financialAccountType;
    private Boolean isDefault;
    private String owner;
}
