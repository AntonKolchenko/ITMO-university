#include "pool.h"

#include <algorithm>
#include <vector>

void * PoolAllocator::allocate(const std::size_t n)
{
    const std::size_t number = std::lower_bound(m_sizes.begin(), m_sizes.end(), n) - m_sizes.begin();
    if (number < m_used_map.size()) {
        const auto iter = std::find(m_used_map[number].begin(), m_used_map[number].end(), false);
        if (iter != m_used_map[number].end()) {
            std::size_t pos = iter - m_used_map[number].begin();
            m_used_map[number][pos] = true;
            return &m_storage[number * capacity + pos * m_sizes[number]];
        }
    }
    throw std::bad_alloc{};
}

void PoolAllocator::deallocate(const void * ptr)
{
    const std::byte * pointer_position = static_cast<const std::byte *>(ptr);
    const std::byte * begin = &m_storage[0];

    const std::size_t number = pointer_position - begin;
    const std::size_t number_block = number / capacity;

    if (number_block >= m_sizes.size()) {
        return;
    }

    const std::size_t pos_in_block = (number % capacity) / m_sizes[number_block];
    if ((number % capacity) % m_sizes[number_block] == 0) {
        m_used_map[number_block][pos_in_block] = false;
    }
}
