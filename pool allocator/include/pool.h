#pragma once

#include <algorithm>
#include <cstddef>
#include <initializer_list>
#include <new>
#include <vector>

class PoolAllocator
{
    std::vector<std::size_t> m_sizes;
    std::vector<std::byte> m_storage;
    std::vector<std::vector<bool>> m_used_map;
    const std::size_t capacity;

public:
    PoolAllocator(const std::size_t count, std::initializer_list<std::size_t> sizes)
        : m_sizes(sizes)
        , m_storage(count * sizes.size())
        , m_used_map(sizes.size())
        , capacity(count)
    {
        std::sort(m_sizes.begin(), m_sizes.end());
        for (std::size_t i = 0; i < m_sizes.size(); i++) {
            m_used_map[i].resize(capacity / m_sizes[i]);
        }
    }

    void * allocate(const std::size_t n);
    void deallocate(const void * ptr);
};
