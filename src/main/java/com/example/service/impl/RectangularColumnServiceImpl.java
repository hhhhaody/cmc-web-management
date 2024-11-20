package com.example.service.impl;

import com.example.mapper.RectangularColumnMapper;
import com.example.pojo.RectangularColumn;
import com.example.service.RectangularColumnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;


import java.util.ArrayList;
import java.util.List;

@Service
public class RectangularColumnServiceImpl implements RectangularColumnService {

    @Autowired
    private RectangularColumnMapper rectangularColumnMapper;

    @Override
    @Transactional
    public void insert(RectangularColumn rectangularColumn) {
        if (rectangularColumn != null) {
            rectangularColumn.setConnectionPlateOuterSteelColumn(convertListToString(parseStringToList(rectangularColumn.getConnectionPlateOuterSteelColumn())));
            rectangularColumn.setConnectionPlateCoreColumn(convertListToString(parseStringToList(rectangularColumn.getConnectionPlateCoreColumn())));
            rectangularColumn.setHeightResidual(convertListToString(parseStringToList(rectangularColumn.getHeightResidual())));
            rectangularColumn.setHeightSurfaceAfterGrindingWeld(convertListToString(parseStringToList(rectangularColumn.getHeightSurfaceAfterGrindingWeld())));
            rectangularColumn.setSurfaceRoughnessAfterGrinding(convertListToString(parseStringToList(rectangularColumn.getSurfaceRoughnessAfterGrinding())));
            rectangularColumn.setColumnSectionDimensions(convertListToString(parseStringToList(rectangularColumn.getColumnSectionDimensions())));
            rectangularColumn.setColumnFootSectionDimensions(convertListToString(parseStringToList(rectangularColumn.getColumnFootSectionDimensions())));
            rectangularColumn.setCoreColumnSectionDimensions(convertListToString(parseStringToList(rectangularColumn.getCoreColumnSectionDimensions())));
            rectangularColumn.setRectangularColumnBendingAlongLength(convertListToString(parseStringToList(rectangularColumn.getRectangularColumnBendingAlongLength())));
            rectangularColumn.setRectangularColumnTailSectionDimensions(convertListToString(parseStringToList(rectangularColumn.getRectangularColumnTailSectionDimensions())));
            rectangularColumn.setPressureReliefHoleDiameter(convertListToString(parseStringToList(rectangularColumn.getPressureReliefHoleDiameter())));
            rectangularColumn.setConnectionHoleLengthWidth(convertListToString(parseStringToList(rectangularColumn.getConnectionHoleLengthWidth())));
            rectangularColumn.setConnectionHoleSpacing(convertListToString(parseStringToList(rectangularColumn.getConnectionHoleSpacing())));
            rectangularColumn.setBottomToTopDistance(convertListToString(parseStringToList(rectangularColumn.getBottomToTopDistance())));
            rectangularColumn.setTopToCoreTopDistance(convertListToString(parseStringToList(rectangularColumn.getTopToCoreTopDistance())));

            rectangularColumnMapper.insert(rectangularColumn);
        }
    }

    private List<Double> parseStringToList(String dataString) {
        List<Double> dataList = new ArrayList<>();
        if (dataString != null && !dataString.isEmpty()) {
            String[] pairs = dataString.split(",");
            for (String pair : pairs) {
                String[] keyValue = pair.split(":");
                try {
                    dataList.add(Double.parseDouble(keyValue[1].trim()));
                } catch (NumberFormatException e) {
                    dataList.add(0.0);
                }
            }
        }
        return dataList;
    }

    private String convertListToString(List<Double> dataList) {
        return dataList.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(", "));
    }
}


