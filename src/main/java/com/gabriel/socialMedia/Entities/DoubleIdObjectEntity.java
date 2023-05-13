package com.gabriel.socialMedia.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoubleIdObjectEntity {
    private UUID id1;
    private UUID id2;
}
