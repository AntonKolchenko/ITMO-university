#include "ScapegoatTree.h"

#include <stdexcept> // for std::invalid_argument exception

ScapegoatTree::node::node(int val, node * parent)
    : parent(parent)
    , value(val)
{
}

void ScapegoatTree::node::pull()
{
    this->size = 1;
    if (this->l) {
        this->size += this->l->size;
    }
    if (this->r) {
        this->size += this->r->size;
    }
}

ScapegoatTree::ScapegoatTree()
    : ScapegoatTree(defaultAlpha)
{
}

ScapegoatTree::ScapegoatTree(double alpha)
    : alpha(alpha)
{
    if (alpha < badAlphaArgument) {
        throw std::invalid_argument("invalid argument");
    }
}

ScapegoatTree::~ScapegoatTree()
{
    clear(root);
}

void ScapegoatTree::correcter(node * pointer)
{
    if (!isBadNode(pointer)) {
        return;
    }
    node * new_pointer = pointer->parent;
    std::vector<int> vctr = clear(pointer);
    make_tree(new_pointer, vctr);
}

void ScapegoatTree::make_tree(node * pointer, std::vector<int> & vctr)
{
    make_tree(pointer, vctr, 0, vctr.size() - 1);
}

void ScapegoatTree::make_tree(node * pointer, std::vector<int> & vctr, int l, int r)
{
    int mid = l + (r - l) / 2;

    if (pointer == nullptr) {
        root = new node(vctr[mid], nullptr);
        pointer = root;
    }
    else {
        if (pointer->value > vctr[r]) {
            pointer->l = new node(vctr[mid], pointer);
            pointer = pointer->l;
        }
        else {
            pointer->r = new node(vctr[mid], pointer);
            pointer = pointer->r;
        }
    }

    if (l <= mid - 1) {
        make_tree(pointer, vctr, l, mid - 1);
    }
    if (mid + 1 <= r) {
        make_tree(pointer, vctr, mid + 1, r);
    }

    pointer->pull();
}

bool ScapegoatTree::isBadNode(node * pointer) const
{
    return (pointer->l != nullptr && pointer->size * alpha < pointer->l->size) ||
            (pointer->r != nullptr && pointer->size * alpha < pointer->r->size);
}

std::vector<int> ScapegoatTree::clear(node * pointer)
{
    std::vector<int> vctr;
    clear(pointer, vctr);
    return vctr;
}

void ScapegoatTree::clear(node * pointer, std::vector<int> & v)
{
    if (pointer == nullptr) {
        return;
    }

    clear(pointer->l, v);
    v.push_back(pointer->value);
    clear(pointer->r, v);

    if (pointer->parent == nullptr) {
        delete root;
        root = nullptr;
        return;
    }

    node * delete_point = pointer->parent;
    std::swap(delete_point, pointer);

    if (pointer->r == delete_point) {
        pointer->r = nullptr;
    }
    else {
        pointer->l = nullptr;
    }
    delete delete_point;
}

bool ScapegoatTree::insert(int value)
{
    if (contains(value)) {
        return false;
    }
    treeSize++;
    insert(root, value);
    return true;
}

void ScapegoatTree::insert(node * pointer, int value)
{
    if (pointer == nullptr) {
        root = new node(value, nullptr);
        return;
    }

    if (pointer->value > value) {
        if (pointer->l) {
            insert(pointer->l, value);
        }
        else {
            pointer->l = new node(value, pointer);
        }
    }
    else {
        if (pointer->r) {
            insert(pointer->r, value);
        }
        else {
            pointer->r = new node(value, pointer);
        }
    }
    pointer->pull();
    correcter(pointer);
}

int ScapegoatTree::erase_min(node * pointer)
{
    int value;
    if (pointer->l) {
        value = erase_min(pointer->l);
        pointer->pull();
        correcter(pointer);
    }
    else {
        value = pointer->value;
        remove(pointer, pointer->value);
    }
    return value;
}

bool ScapegoatTree::remove(int value)
{
    if (!contains(value)) {
        return false;
    }
    treeSize--;
    remove(root, value);
    return true;
}

void ScapegoatTree::remove(node * pointer, int value)
{
    if (pointer->value != value) {
        if (pointer->value > value) {
            remove(pointer->l, value);
        }
        else {
            remove(pointer->r, value);
        }
        pointer->pull();
        correcter(pointer);
        return;
    }

    if (pointer->l != nullptr && pointer->r != nullptr) {
        pointer->value = erase_min(pointer->r);
        pointer->pull();
        correcter(pointer);
        return;
    }

    if (pointer->l == nullptr && pointer->r == nullptr) {
        pointer = pointer->parent;
        if (pointer == nullptr) {
            delete root;
            root = nullptr;
            return;
        }
        if (pointer->l && pointer->l->value == value) {
            delete pointer->l;
            pointer->l = nullptr;
        }
        else {
            delete pointer->r;
            pointer->r = nullptr;
        }
        return;
    }

    if (pointer->parent == nullptr) {
        pointer = (pointer->l ? pointer->l : pointer->r);
        delete root;
        root = pointer;
        root->parent = nullptr;
    }
    else {
        pointer = pointer->parent;
        node * p;
        if (pointer->l && pointer->l->value == value) {
            p = pointer->l;
            pointer->l = (pointer->l->l ? pointer->l->l : pointer->l->r);
            pointer->l->parent = pointer;
        }
        else {
            p = pointer->r;
            pointer->r = (pointer->r->l ? pointer->r->l : pointer->r->r);
            pointer->r->parent = pointer;
        }
        delete p;
    }
}

std::vector<int> ScapegoatTree::values() const
{
    std::vector<int> vctr;
    vctr.reserve(treeSize);
    values(vctr, root);
    return vctr;
}

void ScapegoatTree::values(std::vector<int> & vctr, node * pointer)
{
    if (pointer == nullptr) {
        return;
    }
    ScapegoatTree::values(vctr, pointer->l);
    vctr.push_back(pointer->value);
    ScapegoatTree::values(vctr, pointer->r);
}

bool ScapegoatTree::contains(int value) const
{
    node * pointer = root;
    while (pointer != nullptr) {
        if (pointer->value == value) {
            return true;
        }
        pointer = (pointer->value < value ? pointer->r : pointer->l);
    }
    return false;
}

bool ScapegoatTree::empty() const
{
    return treeSize == 0;
}

std::size_t ScapegoatTree::size() const
{
    return treeSize;
}
