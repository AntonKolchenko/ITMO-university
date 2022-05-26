#pragma once

#include "allocator.h"

#include <algorithm>
#include <cstddef>
#include <list>
#include <new>
#include <ostream>

template <class Key, class KeyProvider, class Allocator>
class Cache
{
    struct Node
    {
        KeyProvider * pointer;
        bool flag;
        Node(KeyProvider * pointer, bool flag)
            : pointer(pointer)
            , flag(flag)
        {
        }
    };

public:
    template <class... AllocArgs>
    Cache(const std::size_t cache_size, AllocArgs &&... alloc_args)
        : m_max_size(cache_size)
        , m_alloc(std::forward<AllocArgs>(alloc_args)...)
    {
    }

    std::size_t size() const
    {
        return m_queue.size();
    }

    bool empty() const
    {
        return m_queue.empty();
    }

    template <class T>
    T & get(const Key & key);

    std::ostream & print(std::ostream & strm) const;

    friend std::ostream & operator<<(std::ostream & strm, const Cache & cache)
    {
        return cache.print(strm);
    }

private:
    const std::size_t m_max_size;
    Allocator m_alloc;
    std::list<Node> m_queue;
};

template <class Key, class KeyProvider, class Allocator>
template <class T>
inline T & Cache<Key, KeyProvider, Allocator>::get(const Key & key)
{
    auto it = std::find_if(m_queue.begin(), m_queue.end(), [&key](const Node & ptr) {
        return *ptr.pointer == key;
    });

    if (it != m_queue.end()) {
        it->flag = 1;
        return *static_cast<T *>(it->pointer);
    }

    while (m_max_size == m_queue.size()) {
        auto local_pair = m_queue.front();
        m_queue.pop_front();
        if (local_pair.flag) {
            local_pair.flag = false;
            m_queue.push_back(local_pair);
        }
        else {
            m_alloc.template destroy<KeyProvider>(local_pair.pointer);
        }
    }

    T * pointer = m_alloc.template create<T>(key);
    m_queue.emplace_back(pointer, false);
    return *pointer;
}

template <class Key, class KeyProvider, class Allocator>
inline std::ostream & Cache<Key, KeyProvider, Allocator>::print(std::ostream & strm) const
{
    bool first = true;
    for (const auto & str : m_queue) {
        if (!first) {
            strm << " ";
        }
        first = false;
        strm << *str.pointer;
    }
    return strm << std::endl;
}
