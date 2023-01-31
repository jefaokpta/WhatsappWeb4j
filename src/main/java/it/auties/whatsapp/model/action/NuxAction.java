package it.auties.whatsapp.model.action;

import static it.auties.protobuf.base.ProtobufType.BOOL;

import it.auties.protobuf.base.ProtobufName;
import it.auties.protobuf.base.ProtobufProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.jackson.Jacksonized;

/**
 * Unknown
 */
@AllArgsConstructor
@Data
@Accessors(fluent = true)
@Jacksonized
@Builder
@ProtobufName("NuxAction")
public final class NuxAction
    implements Action {

  @ProtobufProperty(index = 1, name = "acknowledged", type = BOOL)
  private boolean acknowledged;

  /**
   * Always throws an exception as this action cannot be serialized
   *
   * @return an exception
   */
  @Override
  public String indexName() {
    throw new UnsupportedOperationException("Cannot send action: no index name");
  }
}
