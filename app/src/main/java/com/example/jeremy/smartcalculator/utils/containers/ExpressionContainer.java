package com.example.jeremy.smartcalculator.utils.containers;
import com.example.jeremy.smartcalculator.models.IExpression;

import java.util.ArrayList;
import java.util.List;

public class ExpressionContainer implements IContainer<IExpression> {

    private List<IExpression> expressions;

    public ExpressionContainer() {
        expressions = new ArrayList<>();
    }

    @Override
    public void clear() {
        expressions.clear();
    }

    @Override
    public int size() {
        return expressions.size();
    }

    @Override
    public void add(IExpression iExpression) {
        expressions.add(iExpression);
    }

    @Override
    public IExpression get(int position) {
        return expressions.get(position);
    }

    @Override
    public List<IExpression> getAll() {
        return expressions;
    }
}


      
