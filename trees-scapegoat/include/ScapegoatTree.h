#pragma once

#include <vector>

class ScapegoatTree
{
    struct node
    {
        node * l = nullptr;
        node * r = nullptr;
        node * parent = nullptr;

        int value = 0;
        std::size_t size = 1;

        node(int val, node * parent);

        void pull();
    };

    constexpr static const double defaultAlpha = 0.8;
    constexpr static const double badAlphaArgument = 0.5;
    const double alpha;
    std::size_t treeSize = 0;
    node * root = nullptr;

public:
    ScapegoatTree();
    ScapegoatTree(double alpha);

    bool insert(int value);
    bool remove(int value);
    bool contains(int value) const;

    std::size_t size() const;
    bool empty() const;

    std::vector<int> values() const;

    ~ScapegoatTree();

private:
    void insert(node * q, int value);
    void remove(node * q, int value);
    int erase_min(node * q);

    static void values(std::vector<int> & v, node * q);

    bool isBadNode(node * q) const;
    void correcter(node * q);

    std::vector<int> clear(node * q);
    void clear(node * q, std::vector<int> & v);

    void make_tree(node * p, std::vector<int> & v, int l, int r);
    void make_tree(node * p, std::vector<int> & v);
};
