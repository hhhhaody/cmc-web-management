package com.example.mapper;

import com.example.pojo.RectangularColumn;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RectangularColumnMapper {

    @Insert("insert into rectangular_columns(" +
            "name, " +
            "batch," +
            "cutface, " +
            "surface, " +
            "appearance, " +
            "rebarWelding," +
            "hole," +
            "rebar," +
            "connectionPlateOuterSteelColumn," +
            "connectionPlateCoreColumn," +
            "heightResidual," +
            "heightSurfaceAfterGrindingWeld," +
            "surfaceRoughnessAfterGrinding," +
            "columnSectionDimensions," +
            "columnFootSectionDimensions," +
            "coreColumnSectionDimensions," +
            "rectangularColumnBendingAlongLength," +
            "rectangularColumnTailSectionDimensions," +
            "pressureReliefHoleDiameter," +
            "connectionHoleLengthWidth," +
            "connectionHoleSpacing," +
            "bottomToTopDistance," +
            "topToCoreTopDistance," +
            "created_at," +
            "updated_at) values (" +
            "#{name}," +
            "#{batch}," +
            "#{cutface}," +
            "#{surface}," +
            "#{appearance}," +
            "#{rebarWelding}," +
            "#{hole}," +
            "#{rebar}," +
            "#{connectionPlateOuterSteelColumn}," +
            "#{connectionPlateCoreColumn}," +
            "#{heightResidual}," +
            "#{heightSurfaceAfterGrindingWeld}," +
            "#{surfaceRoughnessAfterGrinding}," +
            "#{columnSectionDimensions}," +
            "#{columnFootSectionDimensions}," +
            "#{coreColumnSectionDimensions}," +
            "#{rectangularColumnBendingAlongLength}," +
            "#{rectangularColumnTailSectionDimensions}," +
            "#{pressureReliefHoleDiameter}," +
            "#{connectionHoleLengthWidth}," +
            "#{connectionHoleSpacing}," +
            "#{bottomToTopDistance}," +
            "#{topToCoreTopDistance}," +
            "NOW(), NOW())")
    void insert(RectangularColumn rectangularColumn);
}
