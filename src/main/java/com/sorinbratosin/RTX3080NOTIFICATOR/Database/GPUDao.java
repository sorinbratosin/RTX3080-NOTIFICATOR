package com.sorinbratosin.RTX3080NOTIFICATOR.Database;

import org.springframework.data.repository.Repository;

public interface GPUDao extends Repository<GPU, Integer> {

    public void save(GPU gpu);
}
