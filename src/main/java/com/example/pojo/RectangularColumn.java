package com.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * 成品方通柱质量检测表单
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RectangularColumn {
    private int id;
    private String name;
    private String batch;
    private String cutface;
    private String surface;
    private String appearance;
    private String welding;
    private String rebarWelding;
    private String hole;
    private String rebar;
    private String connectionPlateOuterSteelColumn;
    private String connectionPlateCoreColumn;
    private String heightResidual;
    private String heightSurfaceAfterGrindingWeld;
    private String surfaceRoughnessAfterGrinding;
    private String columnSectionDimensions;
    private String columnFootSectionDimensions;
    private String coreColumnSectionDimensions;
    private String rectangularColumnBendingAlongLength;
    private String rectangularColumnTailSectionDimensions;
    private String pressureReliefHoleDiameter;
    private String connectionHoleLengthWidth;
    private String connectionHoleSpacing;
    private String bottomToTopDistance;
    private String topToCoreTopDistance;
    private Timestamp createdAt;
    private Timestamp updatedAt;
}
